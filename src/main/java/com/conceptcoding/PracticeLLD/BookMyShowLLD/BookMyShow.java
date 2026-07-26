package com.conceptcoding.PracticeLLD.BookMyShowLLD;

import com.conceptcoding.PracticeLLD.BookMyShowLLD.controller.BookingController;
import com.conceptcoding.PracticeLLD.BookMyShowLLD.entities.*;
import com.conceptcoding.practice.entities.*;
import com.conceptcoding.PracticeLLD.BookMyShowLLD.enums.SeatCategory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookMyShow {
    public static void main(String[] args){
        Movie baahubali = new Movie("baahubali");
        System.out.println("Movie Name:"+ baahubali.getName());
        List<Seat> seats = new ArrayList<>();
        for(int i=1;i<=10;i++){
            seats.add(new Seat(i, SeatCategory.PREMIUM));
        }
        for(int i=11;i<=20;i++){
            seats.add(new Seat(i, SeatCategory.REGULAR));
        }

        Screen inoxScreen1 = new Screen(1, seats);

        Show inoxMorningShowToday = new Show(
                baahubali,
                inoxScreen1,
                LocalDate.now(),
                LocalTime.of(8, 0)
        );

        Show inoxAfternoonShowToday = new Show(
                baahubali,
                inoxScreen1,
                LocalDate.now(),
                LocalTime.of(15, 0)
        );

        inoxScreen1.addShow(inoxMorningShowToday);
        inoxScreen1.addShow(inoxAfternoonShowToday);

        User user = new User("id1", "Anjali kaithal");

        BookingController bookingController = new BookingController();
        List<Integer> selectedSeats = List.of(1, 2, 3);
        Booking booking = bookingController.createBooking(user, inoxMorningShowToday, selectedSeats);
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Booked Seats: " + booking.getSeats());
    }
}
