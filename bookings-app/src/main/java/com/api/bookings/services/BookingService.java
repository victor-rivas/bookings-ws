package com.api.bookings.services;

import com.api.bookings.exceptions.DeleteException;
import com.api.bookings.exceptions.NotFoundException;
import com.api.bookings.exceptions.UpdateException;
import com.api.bookings.repositories.IBookingRepository;
import entities.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final IBookingRepository bookingRepository;

    @Autowired
    public BookingService(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public ArrayList<Booking> getBookings() {
        return bookingRepository.findAllWithUserAndStatus();
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findBookingByIdWithUserAndStatus(id);
    }

    public Booking updateBookingById(Booking booking, Integer id) throws UpdateException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking newBooking = optionalBooking.get();
            newBooking.setStart(booking.getStart());
            newBooking.setEnd(booking.getEnd());
            newBooking.setStatus(booking.getStatus());
            newBooking.setModifiedAt(new Date());

            try {
                bookingRepository.save(newBooking);
                return newBooking;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new UpdateException("Error updating booking", e);
            }
        } else {
            logger.error("Trying to update a booking that doesn't exist");
            throw new UpdateException("Booking not found");
        }
    }

    public Boolean deleteBookingById(Integer id) {
        if (!bookingRepository.existsById(id)) {
            logger.error("Trying to delete a booking that doesn't exist");
            throw new NotFoundException("Trying to delete a booking that doesn't exist");
        }
        try {
            bookingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DeleteException("Error deleting booking", e);
        }
    }
}
