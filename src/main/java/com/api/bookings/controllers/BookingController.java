package com.api.bookings.controllers;

import com.api.bookings.services.BookingService;
import entities.Booking;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ArrayList<Booking> getBookings() {
        return this.bookingService.getBookings();
    }

    @PostMapping
    public Booking saveBooking(@RequestBody Booking booking) {
        return this.bookingService.saveBooking(booking);
    }

    @GetMapping(path = "/{id}")
    public Optional<Booking> getBookingById(@PathVariable("id") Integer id) {
        return this.bookingService.getBookingById(id);
    }

    @PutMapping(path = "/{id}")
    public Booking updateBookingById(@RequestBody Booking booking, @PathVariable("id") Integer id) {
        return this.bookingService.updateBookingById(booking, id);
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteBookingById(@PathVariable("id") Integer id) {
        return this.bookingService.deleteBookingById(id);
    }
}
