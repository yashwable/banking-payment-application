package com.yash.banking.common.generator.impl;

import com.yash.banking.common.generator.EmployeeIdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeIdGeneratorImpl implements EmployeeIdGenerator {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String generate() {
        Long sequence = ((Number) entityManager
                .createNativeQuery("SELECT nextval('employee_id_sequence')")
                .getSingleResult())
                .longValue();

        return String.format("EMP%06d", sequence);
    }
}