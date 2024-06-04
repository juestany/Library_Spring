package com.example.library.service;

import com.example.library.LoginForm;
import com.example.library.infrastructure.entity.UserEntity;
import com.example.library.infrastructure.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for handling user login operations.
 */
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.key}$")
    private String key;

    @Autowired
    public LoginService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates user login credentials and generates a JWT token upon successful authentication.
     *
     * @param loginForm The login form containing the user's credentials.
     * @return JWT token if authentication is successful, otherwise null.
     */
    public String login(LoginForm loginForm){
        UserEntity user = userRepository.getUserByUsername(loginForm.getUsername());
        if(passwordEncoder.matches(loginForm.getPassword(), user.getPassword())){
            long timeMillis = System.currentTimeMillis();
            return Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis+5*60*1000))
                    .claim("id", String.valueOf(user.getId()))
                    .claim("role", user.getRole()) // role from database
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        }
        else {
            return null;
        }

    }
}
