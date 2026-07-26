package com.conceptcoding.PracticeLLD.BookMyShowLLD.controller;

import com.conceptcoding.PracticeLLD.BookMyShowLLD.entities.Booking;
import com.conceptcoding.PracticeLLD.BookMyShowLLD.entities.Show;
import com.conceptcoding.PracticeLLD.BookMyShowLLD.entities.User;
import com.conceptcoding.PracticeLLD.BookMyShowLLD.service.BookingService;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;
    public BookingController() {
        this.bookingService = new BookingService();
    }

    public Booking createBooking(User user, Show show, List<Integer> seats){
        Booking booking = bookingService.book(user, show, seats);
        return booking;
    }
}
