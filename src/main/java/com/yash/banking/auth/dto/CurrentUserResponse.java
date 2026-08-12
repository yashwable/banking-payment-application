package com.yash.banking.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponse {

    private String employeeId;

    private String firstName;

    private String lastName;

    private String officialEmail;

    private String role;
}
