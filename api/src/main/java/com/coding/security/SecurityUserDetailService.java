package com.coding.security;

import com.coding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUserDetailService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(SecurityUserDetailService.class);
    private final UserRepository userRepository;

    public SecurityUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating {}", username);
        return userRepository.findByUsername(username)
                .map(this::createSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found."));
    }

    private User createSecurityUser(com.coding.domain.model.User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + user.getEmail() + " was not activated");
        }
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> role.getName().toString())
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
