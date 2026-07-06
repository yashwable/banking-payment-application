package com.yash.banking.user.mapper.impl;

import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.dto.UserResponse;
import com.yash.banking.user.entity.User;
import com.yash.banking.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setOfficialEmail(request.getOfficialEmail());
        user.setPersonalEmail(request.getPersonalEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .employeeId(user.getEmployeeId())
                .officialEmail(user.getOfficialEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getName())
                .build();
    }
}
