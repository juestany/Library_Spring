package com.example.library.controller;

import com.example.library.infrastructure.entity.UserEntity;
import com.example.library.service.UserService;
import com.example.library.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user-related endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint to retrieve all users.
     * @return List of UserEntity objects representing all users.
     */
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Endpoint to retrieve a specific user by ID.
     * @param id The ID of the user to retrieve.
     * @return The UserEntity representing the requested user.
     */
    @GetMapping("/{id}")
    public UserEntity getOne(@PathVariable long id) {
        return userService.getOne(id);
    }

    /**
     * Endpoint to create a new user.
     * @param user The UserEntity representing the new user to create.
     * @return The created UserEntity.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserEntity create(@RequestBody UserEntity user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userService.create(user);
    }
}
