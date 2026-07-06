package com.yash.banking.config;

import com.yash.banking.user.dto.CreateUserRequest;
import com.yash.banking.user.repository.UserRepository;
import com.yash.banking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class AdminUserInitializer implements CommandLineRunner {

    private static final String ADMIN_EMAIL = "admin@bank.com";

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) {

        if (userRepository.existsByOfficialEmailIgnoreCase(ADMIN_EMAIL)) {
            log.info("Bootstrap administrator already exists. Skipping initialization.");
            return;
        }

        log.info("Creating bootstrap administrator...");

        CreateUserRequest request = new CreateUserRequest();

        request.setOfficialEmail(ADMIN_EMAIL);
        request.setFirstName("System");
        request.setLastName("Administrator");
        request.setPassword("Admin@123");
        request.setRoleName("ADMIN");

        userService.createUser(request);

        log.info("Bootstrap administrator created successfully.");
    }
}