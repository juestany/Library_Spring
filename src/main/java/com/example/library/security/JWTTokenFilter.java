package com.example.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

public class JWTTokenFilter extends OncePerRequestFilter {

    private final String key;

    public JWTTokenFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader!=null && authHeader.startsWith(("Bearer ")) && !authHeader.equals("Bearer undefined")) {
//            System.out.println("Authorization Header: " + authHeader);
            String token = authHeader.split(" ")[1];
            // poniżej - klucz symetryczny, identyczny do zakodowania i odkodowania. dane będą w claims.
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String userId = claims.get("id").toString();
            String userRole = claims.get("role").toString();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null,
                    List.of(new SimpleGrantedAuthority(userRole)));
            // Rola użytkownika powinna być nazwana ROLE_NAZWA
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // jeśli warunki niespelnione to nie dajemy dostepu
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request,response); // ZAWSZE MUSI BYĆ NA KOŃCU FILTRA
    }

}
