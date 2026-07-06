package com.yash.banking.user.repository;

import com.yash.banking.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmployeeId(String employeeId);

    boolean existsByEmployeeId(String employeeId);

    boolean existsByOfficialEmailIgnoreCase(String officialEmail);

    boolean existsByPersonalEmailIgnoreCase(String personalEmail);
}