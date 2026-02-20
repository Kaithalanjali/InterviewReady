package com.conceptcoding.practice.controller;

import com.conceptcoding.practice.entities.Booking;
import com.conceptcoding.practice.entities.Seat;
import com.conceptcoding.practice.entities.Show;
import com.conceptcoding.practice.entities.User;
import com.conceptcoding.practice.service.BookingService;

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
