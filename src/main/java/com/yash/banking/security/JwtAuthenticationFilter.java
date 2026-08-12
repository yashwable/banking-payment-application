package com.yash.banking.security;

import com.yash.banking.auth.service.CustomUserDetailsService;
import com.yash.banking.auth.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(BEARER_PREFIX)) {

            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken =
                authorizationHeader.substring(BEARER_PREFIX.length());

        String employeeId;

        try {

            employeeId = jwtService.extractEmployeeId(jwtToken);
            log.debug("JWT employeeId extracted: {}", employeeId);

        } catch (JwtException | IllegalArgumentException ex) {

            throw new BadCredentialsException("Invalid JWT. ", ex);

        }

        if (employeeId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(employeeId);
            log.debug("User loaded: {}", userDetails.getUsername());

            if (!jwtService.isTokenValid(jwtToken, userDetails)) {
                log.warn("JWT validation failed for employee: {}", employeeId);
                throw new BadCredentialsException("Invalid JWT.");
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            log.debug(
                    "SecurityContext authenticated: {}",
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getName()
            );


        }
        filterChain.doFilter(request, response);

    }

}
