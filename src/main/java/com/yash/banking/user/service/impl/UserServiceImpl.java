package com.yash.banking.user.service.impl;

import com.yash.banking.common.generator.EmployeeIdGenerator;
import com.yash.banking.role.repository.RoleRepository;
import com.yash.banking.user.mapper.UserMapper;
import com.yash.banking.user.repository.UserRepository;
import com.yash.banking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final EmployeeIdGenerator employeeIdGenerator;

}