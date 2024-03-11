package com.api.bookings.repositories;

import entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.role LEFT JOIN FETCH u.logo")
    ArrayList<User> findAllWithRoleAndLogo();

    @Query("SELECT u FROM User u JOIN FETCH u.role LEFT JOIN FETCH u.logo WHERE u.id = :id")
    Optional<User> findUserByIdWithRoleAndLogo(@Param("id") Integer id);

}
