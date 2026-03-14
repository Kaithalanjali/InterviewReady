package com.conceptcoding.uberlld;

import java.util.*;
/*
21. Design a Car Rental System

Design a car rental service . System should support full-day bookings and calculate trip-cost at the end.
Rentals are full-day only and for at least 1 day.
Date format: yyyy-mm-dd (e.g., 2025-08-28).
Core Concepts
A Car has: licensePlate (unique), costPerDay, freeKmsPerDay (free kilometers per day) and costPerKm for extra kilometers that you travel, beyond freeKmsPerDay .

A Booking blocks a car for an inclusive date range [fromDate..tillDate].

A Trip starts with an odometer reading and ends with a final reading and an endDate.

The chargeable day count is computed inclusively:
days = 1 + (effectiveEndDate - fromDate) in whole calendar days.
Examples:
2025-08-28 → 2025-08-28 ⇒ 1 day
2025-08-28 → 2025-08-30 ⇒ 3 days

Effective end date rule at return:
effectiveEndDate = max(tillDate (from booking), endDate (provided at endTrip))
You always charge up to the later of the two dates.
If any trip ends prematurely then we do open that car for booking trips immediately.
e.g. if trip was booked from 2025-08-06 till 2025-08-12 but it ended on 2025-08-09
then after the trip has ended calls to bookCar() will succeed for that vehicle from 2025-08-10 onwards

To make calculations simple,
Both start and end date of trip will always be in same month and same year
Methods
void addCar(String licensePlate, int costPerDay, int freeKmsPerDay, int costPerKm)
licensePlate is non-blank and unique.
Costs and kilometer values are non-negative integers.
If addCar() is called with an existing licensePlate then it should be ignored
boolean bookCar(String orderId, String carLicensePlate, String fromDate, String tillDate)
orderId is non-blank and unique.
carLicensePlate is non-blank and must refer to an existing car.
Dates use yyyy-mm-dd, fromDate ≤ tillDate.
Booking succeeds only if the car is not already booked for any overlapping date(s) with an existing booking.
Overlap is inclusive over calendar days, i.e., two ranges [A..B] and [C..D] overlap if A ≤ D and C ≤ B.
Both fromDate and tillDate will always be in same month and same year
Return: true if successfully booked and all rules pass; otherwise false.
void startTrip(String orderId, int odometerReading)
orderId refers to a previously booked order.
odometerReading is the car’s current odometer at handover (positive integer).
Stores the start odometer for later cost calculation.
int endTrip(String orderId, int finalOdometerReading, String endDate)
orderId, finalOdometerReading and endDate are valid.
Compute cost with the Effective End Date Rule and Distance Rule defined above.
Both fromDate from bookCar() and endDate will always be in same month and same year
bookCar() can't be called for endDate, even if it is a delayed trip end.
e.g. if trip ends on 2025-11-09 then bookCar() for that car can only be called from 2025-11-10
Return: the total trip cost as an integer.
Examples
Example A - Within booked window, no extra kilometers
addCar("KA01AB1234", 1200, 100, 10)

bookCar("ORD-1", "KA01AB1234", "2025-08-28", "2025-08-30")  // 3 days (28,29,30)
→ true

startTrip("ORD-1", 5000)

endTrip("ORD-1", 5250, "2025-08-29")  // endDate earlier than tillDate → charge till 2025-08-30
days = 3
tripKms = 5250 - 5000 = 250
freeAllowance = 3 × 100 = 300
extraKms = max(0, 250 - 300) = 0
totalCost = (3 × 1200) + (0 × 10) = 3600
→ returns 3600
Example B - Extended beyond booked window with extra kilometers
addCar("DL09CD4321", 1500, 120, 8)

bookCar("ORD-2", "DL09CD4321", "2025-09-01", "2025-09-02")  // 2 days (1,2)
→ true

startTrip("ORD-2", 20000)

endTrip("ORD-2", 20550, "2025-09-04")  // extended; effectiveEndDate = 2025-09-04
days = 1 + (2025-09-04 - 2025-09-01) = 4
tripKms = 20550 - 20000 = 550
freeAllowance = 4 × 120 = 480
extraKms = max(0, 550 - 480) = 70
totalCost = (4 × 1500) + (70 × 8) = 6000 + 560 = 6560
→ returns 6560
Example C - Overlapping booking rejected
addCar("MH12EF9999", 1000, 80, 12)

bookCar("ORD-3", "MH12EF9999", "2025-08-10", "2025-08-12")  // 10,11,12
→ true

bookCar("ORD-4", "MH12EF9999", "2025-08-12", "2025-08-15")  // overlaps on 12 (inclusive)
→ false
Constraints & Notes
All data is in memory; no persistence or external systems.
Single process; no concurrency control required.
Inputs will not be null. Assume date strings are well-formed yyyy-mm-dd.
Behavior for impossible values (e.g., finalOdometerReading < startOdometerReading) need not be handled, as inputs are stated valid.
Currency and units are abstract; treat all amounts as integers.
 */
public class CarRentalService {
    class Car{
        String licensePlate;
        int costPerDay;
        int freeKmsPerDay;
        int costPerKm;
        List<Booking> bookings=new ArrayList<>();

        public Car(String licensePlate, int costPerDay, int freeKmsPerDay, int costPerKm){
            this.licensePlate=licensePlate;
            this.costPerDay=costPerDay;
            this.freeKmsPerDay=freeKmsPerDay;
            this.costPerKm=costPerKm;
        }
    }
    class Booking{
        String orderId;
        String licensePlate;
        String fromDate;  // Format: "YYYY-MM-DD"
        String tillDate;   // Format: "YYYY-MM-DD"

        int startOdo;
        String actualEndDate; // Track the actual end date when trip ends
        boolean started=false;
        boolean ended=false;

        public Booking(String orderId, String licensePlate, String fromDate, String tillDate){
            this.orderId=orderId;
            this.licensePlate=licensePlate;
            this.fromDate=fromDate;
            this.tillDate=tillDate;
            this.actualEndDate = tillDate; // Initially set to booked tillDate
        }
    }

    Map<String, Car> cars;
    Map<String, Booking> bookings;

    /**
     * Initialize the car rental service
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public CarRentalService() {
        cars = new HashMap<>();
        bookings = new HashMap<>();
    }

    /**
     * Add a new car to the rental service
     * Time Complexity: O(1) - HashMap put/containsKey operations are O(1) average
     * Space Complexity: O(1) - stores one car object
     */
    public void addCar(String licensePlate, int costPerDay, int freeKmsPerDay, int costPerKm) {
        if(cars.containsKey(licensePlate)){
            return;
        }
        cars.put(licensePlate, new Car(licensePlate, costPerDay, freeKmsPerDay, costPerKm));
    }

    /**
     * Book a car for a specific date range
     * Time Complexity: O(B) where B = number of existing bookings for this specific car
     *   - HashMap lookups: O(1) average
     *   - Iterate through car's bookings: O(B)
     *   - String compareTo: O(1) since date strings are fixed length (10 characters)
     * Space Complexity: O(1) - creates one booking object
     */
    public boolean bookCar(String orderId, String carLicensePlate, String fromDate, String tillDate) {
        Car car = cars.get(carLicensePlate);
        if(car==null){
            return false;
        }
        if(bookings.containsKey(orderId)){
            return false;
        }


        //checking of overlap
        List<Booking> carBookings = car.bookings;
        for(Booking booking : carBookings){
            // Use actualEndDate if trip has ended, otherwise use the booked tillDate
            String effectiveEndDate = booking.ended ? booking.actualEndDate : booking.tillDate;

            // Use full date string comparison for proper overlap detection across months
            // Two bookings overlap if: existing starts <= new ends AND new starts <= existing ends
            if(booking.fromDate.compareTo(tillDate) <= 0 && fromDate.compareTo(effectiveEndDate) <= 0){
                return false;
            }
        }

        Booking booking = new Booking(orderId, carLicensePlate, fromDate, tillDate);
        car.bookings.add(booking);
        bookings.put(orderId, booking);
        return true;
    }

    /**
     * Start a trip for a booked order
     * Time Complexity: O(1)
     *   - HashMap containsKey: O(1) average
     *   - HashMap get: O(1) average
     *   - Field assignments: O(1)
     * Space Complexity: O(1) - only updates existing booking object
     */
    public void startTrip(String orderId, int odometerReading) {
        if(bookings.containsKey(orderId)){
            Booking booking = bookings.get(orderId);
            booking.startOdo = odometerReading;
            booking.started=true;
        }
    }

    /**
     * End a trip and calculate the total cost
     * Time Complexity: O(1)
     *   - HashMap operations: O(1) average
     *   - String compareTo: O(1) since date strings are fixed length (10 characters)
     *   - String substring: O(1) since extracting fixed positions
     *   - Arithmetic operations: O(1)
     * Space Complexity: O(1) - only updates existing booking object
     */
    public int endTrip(String orderId, int finalOdometerReading, String endDate) {
        if(!bookings.containsKey(orderId)) {
            return -1;
        }
        Booking booking = bookings.get(orderId);
        if(booking.started && !booking.ended){
            booking.ended=true;
            Car car = cars.get(booking.licensePlate);
            booking.actualEndDate = endDate; // Store the actual end date for availability checking

            // Use the later date for billing (charged till booked date even if returned early)
            String effectiveEndDate = endDate.compareTo(booking.tillDate) > 0 ? endDate : booking.tillDate;

            // Simple day calculation
            int fromDay = Integer.parseInt(booking.fromDate.substring(8));
            int toDay = Integer.parseInt(effectiveEndDate.substring(8));
            int days = 1 + (toDay - fromDay);

            int totalKms = finalOdometerReading-booking.startOdo;
            int extraKms = Math.max(totalKms-car.freeKmsPerDay*days, 0);
            return car.costPerDay*days + extraKms*car.costPerKm;
        }
        return -1;
    }

    public static void main(String[] args) {
        CarRentalService service = new CarRentalService();
        service.addCar("KA01AB1234", 1200, 100, 10);
        System.out.println(service.bookCar("ORD-1", "KA01AB1234", "2025-08-28", "2025-08-30")); // true
        service.startTrip("ORD-1", 5000);
        System.out.println(service.endTrip("ORD-1", 5250, "2025-08-29")); // 3600
    }
}