package com.example.library.service;

import com.example.library.infrastructure.entity.UserEntity;
import com.example.library.infrastructure.repository.UserRepository;
import com.example.library.service.exception.UserAlreadyExistsException;
import com.example.library.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of user entities.
     */
    public List<UserEntity> getAll() {
        return (List<UserEntity>) userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user entity corresponding to the provided ID.
     * @throws UserNotFoundException if no user with the given ID is found.
     */
    public UserEntity getOne(long id) {
        if(id <= 0) {
            throw new IllegalArgumentException("Invalid user ID. Please enter a positive user ID.");
        }
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID '%s' not found.", id)));
    }

    /**
     * Creates a new user.
     *
     * @param user The user entity to be created.
     * @return The created user entity.
     * @throws UserAlreadyExistsException if a user with the same login already exists.
     */
    public UserEntity create(UserEntity user) {
        String login = user.getLogin();
        Optional<UserEntity> existingUser = userRepository.findUserEntityByLogin(login);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with login '%s' already exists.", login));
        } else {
            return userRepository.save(user);
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException if no user with the given ID is found.
     */
    public void delete(long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("User with ID '%s' not found.", id));
        }
        userRepository.deleteById(id);
    }
}
