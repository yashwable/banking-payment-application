package com.yash.banking.auth.service.impl;

import com.yash.banking.auth.dto.LoginRequest;
import com.yash.banking.auth.dto.LoginResponse;
import com.yash.banking.auth.service.AuthenticationService;
import com.yash.banking.common.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public LoginResponse login(LoginRequest request) {

        log.info("Authentication request received for employeeId: {}", request.getEmployeeId());

        throw new InvalidCredentialsException("Invalid employee ID or password.");
    }
}