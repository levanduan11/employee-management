package com.coding.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(this::createAuditor);
    }

    private Long createAuditor(Object principal) {
        if (principal instanceof SecurityUserDetails userDetails) {
            return userDetails.getId();
        }
        if (principal instanceof Jwt jwt) {
            return (Long) jwt.getClaims().get("userId");
        }
        return null;
    }
}
