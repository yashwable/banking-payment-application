package com.yash.banking.user.controller;

import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.dto.UserResponse;
import com.yash.banking.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }
}
