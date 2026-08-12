package com.yash.banking.auth.service.impl;

import com.yash.banking.auth.dto.LoginRequest;
import com.yash.banking.auth.dto.LoginResponse;
import com.yash.banking.auth.model.AuthenticatedUser;
import com.yash.banking.auth.service.AuthenticationService;
import com.yash.banking.auth.service.JwtService;
import com.yash.banking.common.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        log.debug("Authenticating employee {}", request.getEmployeeId());

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmployeeId(),
                                request.getPassword()
                        )
                );

        AuthenticatedUser authenticatedUser =
                (AuthenticatedUser) authentication.getPrincipal();

        String accessToken =
                jwtService.generateToken(authenticatedUser);

        log.info("Employee {} authenticated successfully.",
                authenticatedUser.getUsername());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime() / 1000)
                .build();
    }

}