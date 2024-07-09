package com.example.identityservice.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(loggedInUser) || !loggedInUser.isAuthenticated()) {
            return Optional.of("anonymous");
        }
        String username = loggedInUser.getName();
        return Optional.of(username);
    }
}
