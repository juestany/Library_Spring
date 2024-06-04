package com.example.library.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.token.key}")
    private String key;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(new JWTTokenFilter(key),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/login").permitAll() // kazdy moze do tego wejsc
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/api/books").hasRole("ADMIN")
                                .requestMatchers("/api/users").hasRole("ADMIN")
                                .requestMatchers("/api/loans").hasRole("ADMIN")
                                .requestMatchers("/api/error").permitAll())
                // tutaj dalsza konfiguracja
                // w konfiguracji nie uzywamy prefiksu ROLE_
                .build();
    }
}