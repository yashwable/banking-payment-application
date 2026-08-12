package com.yash.banking.auth.controller;

import com.yash.banking.auth.dto.CurrentUserResponse;
import com.yash.banking.auth.dto.LoginRequest;
import com.yash.banking.auth.dto.LoginResponse;
import com.yash.banking.auth.model.AuthenticatedUser;
import com.yash.banking.auth.service.AuthenticationService;
import com.yash.banking.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        CurrentUserResponse response =
                userService.getCurrentUser(authenticatedUser.getEmployeeId());

        return ResponseEntity.ok(response);
    }
}