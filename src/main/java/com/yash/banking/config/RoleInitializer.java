package com.yash.banking.config;

import com.yash.banking.enums.UserRole;
import com.yash.banking.role.entity.Role;
import com.yash.banking.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        for (UserRole role : UserRole.values()) {

            if (!roleRepository.existsByName(role.name())) {

                Role newRole = new Role(
                        role.name(),
                        role.name() + " role"
                );

                roleRepository.save(newRole);
            }
        }
    }
}