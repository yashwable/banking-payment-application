package com.yash.banking.user.service;

import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.dto.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

}