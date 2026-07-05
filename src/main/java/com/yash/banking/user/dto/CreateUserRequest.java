package com.yash.banking.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @Email
    @NotBlank
    private String officialEmail;

    @Email
    private String personalEmail;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    private String roleName;
}