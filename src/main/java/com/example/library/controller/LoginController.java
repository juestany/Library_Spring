package com.example.library.controller;

import com.example.library.LoginForm;
import com.example.library.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling login-related endpoints.
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Endpoint for user login.
     * @param loginForm The login form containing username and password.
     * @return ResponseEntity containing either a token for successful login or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String token = loginService.login(loginForm);
        if (token == null) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }
}
