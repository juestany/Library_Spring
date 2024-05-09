package com.example.library.controller;

import com.example.library.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling various exceptions thrown by the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserAlreadyExistsException and returns a ResponseEntity with HTTP status 409 Conflict.
     * @param ex The exception to handle.
     * @return ResponseEntity with a message describing the error.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    /**
     * Handles UserNotFoundException and returns a ResponseEntity with HTTP status 404 Not Found.
     * @param ex The exception to handle.
     * @return ResponseEntity with a message describing the error.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles BookNotFoundException and returns a ResponseEntity with HTTP status 404 Not Found.
     * @param ex The exception to handle.
     * @return ResponseEntity with a message describing the error.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles BookAlreadyExistsException and returns a ResponseEntity with HTTP status 409 Conflict.
     * @param ex The exception to handle.
     * @return ResponseEntity with a message describing the error.
     */
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<String> handleBookAlreadyExistsException(BookAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    /**
     * Handles LoanNotFoundException and returns a ResponseEntity with HTTP status 404 Not Found.
     * @param ex The exception to handle.
     * @return ResponseEntity with a message describing the error.
     */
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
