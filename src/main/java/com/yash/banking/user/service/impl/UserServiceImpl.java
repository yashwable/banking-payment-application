package com.yash.banking.user.service.impl;

import com.yash.banking.auth.dto.CurrentUserResponse;
import com.yash.banking.common.exception.DuplicateResourceException;
import com.yash.banking.common.exception.ResourceNotFoundException;
import com.yash.banking.common.generator.EmployeeIdGenerator;
import com.yash.banking.enums.UserStatus;
import com.yash.banking.role.entity.Role;
import com.yash.banking.role.repository.RoleRepository;
import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.dto.UserResponse;
import com.yash.banking.user.entity.User;
import com.yash.banking.user.mapper.UserMapper;
import com.yash.banking.user.repository.UserRepository;
import com.yash.banking.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final EmployeeIdGenerator employeeIdGenerator;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        log.info("Creating employee with official email: {}", request.getOfficialEmail());

        validateDuplicateEmails(request);

        Role role = getRoleByName(request.getRoleName());

        String employeeId = employeeIdGenerator.generate();

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userMapper.toEntity(request);

        populateUser(
                user,
                role,
                employeeId,
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        log.info("Employee {} created successfully.", savedUser.getEmployeeId());

        return userMapper.toResponse(savedUser);
    }

    private void validateDuplicateEmails(CreateUserRequest request) {

        if (userRepository.existsByOfficialEmailIgnoreCase(request.getOfficialEmail())) {
            throw new DuplicateResourceException("Official email already exists.");
        }

        if (StringUtils.hasText(request.getPersonalEmail())
                && userRepository.existsByPersonalEmailIgnoreCase(request.getPersonalEmail())) {

            throw new DuplicateResourceException("Personal email already exists.");
        }
    }

    private Role getRoleByName(String roleName) {

        return roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found: " + roleName));
    }

    private void populateUser(
            User user,
            Role role,
            String employeeId,
            String encodedPassword) {

        user.setEmployeeId(employeeId);

        user.setPassword(encodedPassword);

        user.setRole(role);

        user.setStatus(UserStatus.ACTIVE);

        user.setFailedLoginAttempts(0);

        user.setAccountLocked(false);
    }

    public CurrentUserResponse getCurrentUser(String employeeId) {
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Employee not found: " + employeeId));

        return CurrentUserResponse.builder()
                .employeeId(user.getEmployeeId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .officialEmail(user.getOfficialEmail())
                .role(user.getRole().getName())
                .build();
    }


}