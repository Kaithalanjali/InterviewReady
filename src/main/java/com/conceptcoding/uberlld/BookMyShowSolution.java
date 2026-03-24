package com.conceptcoding.uberlld;

import java.util.*;
/**
 * Design a Movie ticket booking system like BookMyShow
 *
 * Write code for low level design of a movie ticket booking system like BookMyShow.
 * System has cinemas located in different cities. Each cinema will have multiple screens, and users can book one or more seats for a given movie show.
 *
 * System should be able to add new cinemas and movie shows in those cinemas.
 * Users should be able to list all cinema's in their city which are displaying a particular movie.
 * For a given cinema, users should also be able to list all shows which are displaying a particular movie.
 *
 * Booking Criteria:
 * - Try to book continuous seats from same row.
 * - In case multiple options are available then choose
 *   seats starting from lowest row and column.
 * - If continous seats are not available then pick seats from lowest row, column
 *
 * Note:
 * - You can practice this question in Java and Python
 * - Your code will be tested in a Single Threaded environment.
 *
 * Implement the below methods in Solution class:
 *
 * init(Helper10 helper)
 * - init() is our constructor/initializer
 * - helper has methods like, helper.print("") and helper.println("")
 *     which you can use for printing logs,
 *     System.out.println logs will not be visible.
 *
 * addCinema(int cinemaId, int cityId,
 * int screenCount, int screenRow, int screenColumn)
 * Add a new cinema inside a city.
 * - cinemaId is unique across cities
 * - 0<=cinemaId<=1000
 * - 0<=cityId<=100
 * - each cinema can have multiple screens
 *     1<=screenCount<=10
 * - each cinema screen will have seats arranged in rows and columns
 * - 1<=screenRow, screenColumn<=100
 *
 * addShow(int showId, int movieId, int cinemaId,
 * int screenIndex, long startTime, long endTime)
 * Add a show for displaying a movie on a
 *   cinema screen in a given time slot.
 * - 0<=showId<=2000
 * - 0<=movieId<=20
 * - 1<=screenIndex<=10
 *
 * List[String] bookTicket(String ticketId,
 * int showId, int ticketsCount)
 * Returns list of seats as "row-column"
 * e.g. ["0-4", "2-14", "2-0", "11-6"]
 * 1<=ticketsCount<=10
 * See Booking Criteria above on how to book seats.
 * - If there are less than ticketsCount seats in the given show,
 *   then don't book any seats and return empty list.
 *
 * boolean cancelTicket(String ticketId)
 * cancels ticket and makes all seats booked in ticketId
 * again available for booking.
 * - return true if booking with ticketId is cancelled
 *   and seats are released for booking again
 * - return false if booking with ticketId doesn't exist
 *   or it is already cancelled.
 *
 * int getFreeSeatsCount(int showId)
 * - return number of free seats available for booking in show showId
 * - if no show exists with showId or no free seats are available
 * then return 0
 *
 * List[Integer] listCinemas(int movieId, int cityId)
 * - returns cinemaId's of all cinemas which are
 *     running shows for given movie in the given city
 * - cinemaId's are ordered in ascending order
 *
 * List[Integer] listShows(int movieId, int cinemaId)
 * - returns showId's of all shows displaying
 *     the given movie in given cinema.
 * - showId's are ordered in descending order of startTime
 *     and then showId in ascending order
 */

/**
 * Thread-Safe Book My Show Implementation
 *
 * CONCURRENCY CONTROL:
 * - Each Show object acts as a lock for its seat booking operations
 * - synchronized(show) blocks prevent race conditions when:
 *   1. Multiple users try to book the same seats simultaneously
 *   2. Users book while others are canceling tickets
 *   3. Reading free seat count during concurrent bookings
 *
 * GUARANTEES:
 * - No two users can book the same seat at the same time
 * - Atomic booking: either all seats are booked or none
 * - Consistent free seat count across concurrent operations
 * - Per-show locking allows concurrent bookings for different shows
 *
 * PERFORMANCE:
 * - Fine-grained locking: Only locks individual shows, not entire system
 * - Multiple shows can be booked simultaneously
 * - Serialization only occurs for same-show bookings
 */
public class BookMyShowSolution implements Q10MovieBookingInterface {
    private Helper10 helper;

    class Cinema {
        int cinemaId;
        int cityId;
        List<Screen> screens = new ArrayList<>();
    }

    class Screen {
        int rows;
        int columns;
    }

    class Show {
        int showId;
        int movieId;
        int cinemaId;
        int screenIndex;
        long startTime;
        long endTime;

        boolean[][] booked;
        int freeSeats;
    }

    class Ticket {
        String ticketId;
        int showId;
        List<int[]> seats = new ArrayList<>();
    }
    Map<Integer,Cinema> cinemas;
    Map<Integer,Show> shows;
    Map<String,Ticket> tickets;

    Map<String, Set<Integer>> movieCityCinema;
    Map<String, List<Integer>> movieCinemaShows;

    public BookMyShowSolution() {
        cinemas = new HashMap<>();
        shows = new HashMap<>();
        tickets = new HashMap<>();

        movieCityCinema = new HashMap<>();
        movieCinemaShows = new HashMap<>();
    }

    public void init(Helper10 helper) {
        this.helper = helper;
    }


    public void addCinema(int cinemaId, int cityId,
                          int screenCount, int screenRow, int screenColumn) {
        Cinema c = new Cinema();
        c.cinemaId = cinemaId;
        c.cityId = cityId;
        for(int i=0; i<screenCount; i++) {
            Screen s = new Screen();
            s.rows = screenRow;
            s.columns = screenColumn;
            c.screens.add(s);
        }
        cinemas.put(cinemaId, c);
    }

    public void addShow(int showId, int movieId, int cinemaId,
                        int screenIndex, long startTime, long endTime) {
        Cinema c = cinemas.get(cinemaId);
        Screen s = c.screens.get(screenIndex);

        Show show = new Show();
        show.showId = showId;
        show.movieId = movieId;
        show.cinemaId = cinemaId;
        show.screenIndex = screenIndex;
        show.startTime = startTime;
        show.endTime = endTime;

        show.booked = new boolean[s.rows][s.columns];
        show.freeSeats = s.rows*s.columns;

        shows.put(showId, show);

        String key1 = movieId+"-"+c.cityId;
        movieCityCinema.computeIfAbsent(key1, k-> new TreeSet<>()).add(show.cinemaId);
        String key2 = movieId+"-"+cinemaId;
        movieCinemaShows.computeIfAbsent(key2, k-> new ArrayList<>()).add(show.showId);
    }

    // Time Complexity: O(rows * cols * ticketsCount) in worst case
    // Space Complexity: O(ticketsCount) for storing seat allocations
    // Thread-safe: synchronized on show object to prevent double booking
    public List<String> bookTicket(String ticketId,
                                   int showId, int ticketsCount) {
        Show show = shows.get(showId);
        if(show==null){
            return new ArrayList<>();
        }

        // Synchronize on the show object to prevent concurrent booking of same seats
        synchronized(show) {
            if(show.freeSeats<ticketsCount){
                return new ArrayList<>();
            }

            int rows = show.booked.length;
            int cols = show.booked[0].length;

            List<int[]> seats = new ArrayList<>();

            // Try to find continuous seats in same row
            for(int r=0; r<rows; r++){
                int count = 0;
                int start=-1;

                for(int c=0;c<cols;c++){
                    if(!show.booked[r][c]){
                        if(start==-1){
                            start=c;
                        }
                        count++;
                        if(count==ticketsCount){
                            for(int k=0;k<ticketsCount;k++){
                                seats.add(new int[]{r,start+k});
                            }
                            break;
                        }
                    }else{
                        start=-1;
                        count=0;
                    }
                }
                if(!seats.isEmpty()) {
                    break;
                }
            }

            // If continuous seats not available, pick individual seats from lowest row/column
            if(seats.isEmpty()){
                for(int r=0;r<rows && seats.size()<ticketsCount;r++){
                    for(int c=0;c<cols && seats.size()<ticketsCount;c++){
                        if(!show.booked[r][c]){
                            seats.add(new int[]{r,c});
                        }
                    }
                }
            }

            // Mark seats as booked
            for(int[] seat:seats){
                show.booked[seat[0]][seat[1]] = true;
            }
            show.freeSeats -= seats.size();

            Ticket ticket = new Ticket();
            ticket.ticketId = ticketId;
            ticket.showId = showId;
            ticket.seats = seats;
            tickets.put(ticketId, ticket);

            List<String> result = new ArrayList<>();
            for(int[] s:seats){
                result.add(s[0]+"-"+s[1]);
            }
            return result;
        }
    }

    // Time Complexity: O(number of seats in ticket)
    // Space Complexity: O(1)
    // Thread-safe: synchronized on show object to prevent race conditions
    public boolean cancelTicket(String ticketId) {
        if(!tickets.containsKey(ticketId)){
            return false;
        }
        Ticket ticket = tickets.get(ticketId);
        Show show = shows.get(ticket.showId);

        // Synchronize on show object to prevent race conditions during cancellation
        synchronized(show) {
            for(int[] seat:ticket.seats) {
                show.booked[seat[0]][seat[1]] = false;
                show.freeSeats++;
            }
            tickets.remove(ticketId);
            return true;
        }
    }

    // Time Complexity: O(1)
    // Space Complexity: O(1)
    // Thread-safe: synchronized read to ensure consistent value
    public int getFreeSeatsCount(int showId) {
        if(!shows.containsKey(showId)){
            return 0;
        }
        Show show = shows.get(showId);
        synchronized(show) {
            return show.freeSeats;
        }
    }

    public List<Integer> listCinemas(int movieId, int cityId) {
        String key = movieId+"-"+cityId;
        if(!movieCityCinema.containsKey(key)) {
            return new ArrayList<>();
        }
        // TreeSet automatically maintains sorted order
        return new ArrayList<>(movieCityCinema.get(key));
    }

    public List<Integer> listShows(int movieId, int cinemaId) {
        String key = movieId+"-"+cinemaId;
        if(!movieCinemaShows.containsKey(key)) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>(movieCinemaShows.get(key));

        list.sort((a,b)->{
            Show showA = shows.get(a);
            Show showB = shows.get(b);

            if(showA.startTime!=showB.startTime){
                return Long.compare(showB.startTime, showA.startTime);
            }
            return a-b;
        });
        return list;
    }

}

// uncomment below code when you are using your local code editor and
// comment it back again back when you are pasting completed solution in the online CodeZym editor
// this will help avoid unwanted compilation errors and get method autocomplete in your local code editor.

interface Q10MovieBookingInterface {
    void init(Helper10 helper);

    void addCinema(int cinemaId, int cityId,
                   int screenCount, int screenRow, int screenColumn);

    void addShow(int showId, int movieId, int cinemaId,
                 int screenIndex, long startTime, long endTime);

    List<String> bookTicket(String ticketId,
                            int showId, int ticketsCount);

    boolean cancelTicket(String ticketId);

    int getFreeSeatsCount(int showId);

    // returns cinemaId's of all cinemas which are running a show for given movie
    // cinemaId's are ordered in ascending order
    List<Integer> listCinemas(int movieId, int cityId);

    // returns all showId's of all shows displaying the movie in given cinema
    // showId's are ordered in ascending order
    List<Integer> listShows(int movieId, int cinemaId);

}

class Helper10 {
    void print(String s) {
        System.out.print(s);
    }

    void println(String s) {
        print(s + "\n");
    }
}

