package com.api.bookings.services;

import com.api.bookings.exceptions.DeleteException;
import com.api.bookings.exceptions.NotFoundException;
import com.api.bookings.exceptions.UpdateException;
import com.api.bookings.repositories.IUserRepository;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User updateUserById(User user, Integer id) throws UpdateException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user1 = optionalUser.get();
            user1.setName(user.getName());
            user1.setSurname(user.getSurname());
            user1.setPassword(user.getPassword());
            user1.setRole(user.getRole());
            user1.setEmail(user.getEmail());
            user1.setTelephone(user.getTelephone());
            user1.setLogo(user.getLogo());
            user1.setCreatedAt(user.getCreatedAt());

            try {
                userRepository.save(user1);
                return user1;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new UpdateException("Error updating user", e);
            }
        } else {
            logger.error("Trying to update a user that doesn't exist");
            throw new UpdateException("User not found");
        }
    }

    public Boolean deleteUserById(Integer id) {
        if (!userRepository.existsById(id)) {
            logger.error("Trying to delete a user that doesn't exist");
            throw new NotFoundException("Trying to delete a user that doesn't exist");
        }
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DeleteException("Error deleting user", e);
        }
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Aquí debes implementar la verificación de la contraseña.
            // Esto es un ejemplo simple y NO seguro para fines ilustrativos. Debes usar hashing de contraseñas.
            return user.getPassword().equals(password);
        }
        return false;
    }
}
