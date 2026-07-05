package com.yash.banking.user.mapper;

import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.dto.UserResponse;
import com.yash.banking.user.entity.User;

public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);

}