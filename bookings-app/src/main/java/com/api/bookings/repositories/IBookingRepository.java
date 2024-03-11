package com.api.bookings.repositories;

import entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.status")
    ArrayList<Booking> findAllWithUserAndStatus();

    @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.status WHERE b.id = :id")
    Optional<Booking> findBookingByIdWithUserAndStatus(@Param("id") Integer id);

}
