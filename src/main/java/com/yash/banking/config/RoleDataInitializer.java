package com.yash.banking.config;

import com.yash.banking.entity.Role;
import com.yash.banking.enums.UserRole;
import com.yash.banking.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

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