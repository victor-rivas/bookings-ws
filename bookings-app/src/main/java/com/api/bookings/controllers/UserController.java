package com.api.bookings.controllers;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.bookings.services.UserService;
import request.LoginRequest;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ArrayList<User> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getUserById(@PathVariable("id") Integer id) {
        return this.userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable("id") Integer id) {
        return this.userService.updateUserById(user, id);
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteUserById(@PathVariable("id") Integer id) {
        return this.userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        if (userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
}
