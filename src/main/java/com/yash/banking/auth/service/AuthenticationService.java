package com.yash.banking.auth.service;

import com.yash.banking.auth.dto.LoginRequest;
import com.yash.banking.auth.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}