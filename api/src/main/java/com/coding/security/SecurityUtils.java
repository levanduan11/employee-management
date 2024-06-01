package com.coding.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public final class SecurityUtils {
    public static Optional<String> extractUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SecurityUtils::getUsernameFromPrincipal);
    }

    private static String getUsernameFromPrincipal(Object principal) {
        if (principal instanceof SecurityUserDetails userDetails) {
            return userDetails.getUsername();
        }
        if (principal instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        if (principal instanceof String username) {
            return username;
        }
        return null;
    }

    public static boolean isAuthenticated() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .isPresent();
    }

    public static boolean hasCurrentUserAnyOrRoles(String... roles) {
        return isAuthenticated() && getRoles().anyMatch(role -> Arrays.asList(roles).contains(role));
    }

    public static boolean hasCurrentUserAllOrRoles(String... roles) {
        return isAuthenticated() && getRoles().allMatch(role -> Arrays.asList(roles).contains(role));
    }

    public static boolean hasCurrentUserNoneOrRoles(String... roles) {
        return !hasCurrentUserAnyOrRoles(roles);
    }

    public static boolean hasCurrentUserThisRole(String role) {
        return hasCurrentUserAnyOrRoles(role);
    }

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated);
    }

    public static Stream<String> getRoles() {
        return getAuthentication()
                .map(Authentication::getAuthorities)
                .stream()
                .flatMap(grantedAuthorities -> grantedAuthorities
                        .stream()
                        .map(GrantedAuthority::getAuthority));
    }

    private SecurityUtils() {

    }

}
