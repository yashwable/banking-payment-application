package com.yash.banking.auth.service;

import com.yash.banking.auth.model.AuthenticatedUser;
import com.yash.banking.user.entity.User;
import com.yash.banking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) {

        log.debug("Loading employee with employeeId: {}", employeeId);

        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Employee not found: " + employeeId));

        return new AuthenticatedUser(
                user.getId(),
                user.getEmployeeId(),
                user.getPassword(),
                user.getStatus(),
                user.getAccountLocked(),
                user.getRole().getName()
        );
    }


}
