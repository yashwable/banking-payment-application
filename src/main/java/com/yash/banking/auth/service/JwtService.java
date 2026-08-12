package com.yash.banking.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails user);

    String extractEmployeeId(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    long getExpirationTime();
}
