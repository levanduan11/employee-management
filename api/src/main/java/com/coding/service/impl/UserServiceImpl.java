package com.coding.service.impl;

import com.coding.config.AppProperties;
import com.coding.core.exception.EmailAlreadyUsedException;
import com.coding.core.exception.UsernameAlreadyUsedException;
import com.coding.core.util.RandomUtil;
import com.coding.domain.SendMail;
import com.coding.domain.enumeration.ActivationUser;
import com.coding.domain.model.Role;
import com.coding.domain.model.User;
import com.coding.repository.RoleRepository;
import com.coding.repository.UserRepository;
import com.coding.service.MailService;
import com.coding.service.UserService;
import com.coding.web.request.RegisUserRequest;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final AppProperties appProperties;

    public UserServiceImpl(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final RoleRepository roleRepository,
            final MailService mailService,
            final AppProperties appProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
        this.appProperties = appProperties;
    }

    @Override
    public Long register(RegisUserRequest request) {
        Preconditions.checkArgument(request != null, "request must not be null");
        log.info("register user: {}", request);
        userRepository.findByUsername(request.username())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException(existingUser.getUsername());
                    }
                });
        userRepository.findByEmail(request.email())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException(existingUser.getEmail());
                    }
                });
        User user = new User();
        user.setUsername(request.username());
        String randomPassword = RandomUtil.randomAlphabetic(8);
        user.setPassword(passwordEncoder.encode(randomPassword));
        user.setEmail(request.email());
        user.setActivated(false);
        user.setActivationKey(RandomUtil.randomAlphabetic(32));
        user.setActivationDate(LocalDateTime.now());
        Set<Role> roles = request.roles().stream()
                .map(roleString -> {
                    String r = roleString.toUpperCase();
                    if (!r.startsWith("ROLE_")) {
                        r = "ROLE_" + r;
                    }
                    return r;
                })
                .filter(s -> Arrays.stream(Role.RoleName.values()).anyMatch(roleName -> roleName.name().equals(s)))
                .map(Role.RoleName::valueOf)
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
        String url = appProperties.ui_url();
        String verifyUrl = url + "/activate?key=" + user.getActivationKey();
        Map<String, Object> variables = ActivationUser.variables(user.getUsername(), verifyUrl);
        SendMail sendMail = SendMail.builder()
                .to(new String[]{user.getEmail()})
                .build();
        mailService.sendActivationMail(sendMail, variables);
        log.info("register success, username: {}", request.username());
        return user.getId();
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}
