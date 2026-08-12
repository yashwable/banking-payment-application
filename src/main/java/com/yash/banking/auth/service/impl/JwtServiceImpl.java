package com.yash.banking.auth.service.impl;

import com.yash.banking.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${bank.security.jwt.secret}")
    private String secretKey;

    @Value("${bank.security.jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(Date.from(Instant.now()));
    }

    @Override
    public String generateToken(UserDetails userDetails) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtExpiration)))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractEmployeeId(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    @Override
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String employeeId = extractEmployeeId(token);

        return employeeId.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    @Override
    public long getExpirationTime() {
        return jwtExpiration;
    }



}
