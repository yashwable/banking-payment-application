package com.yash.banking.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String employeeId;

    private String officialEmail;

    private String firstName;

    private String lastName;

    private String role;

}