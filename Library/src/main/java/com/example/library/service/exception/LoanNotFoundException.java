package com.example.library.service.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException (String message) {
        super(message);
    }
}
