package com.coding.service.impl;

import com.coding.config.AppProperties;
import com.coding.core.constant.MessageKeyConstant;
import com.coding.core.exception.*;
import com.coding.core.util.FileUtils;
import com.coding.core.util.RandomUtil;
import com.coding.domain.SendMail;
import com.coding.domain.enumeration.ActivationUser;
import com.coding.domain.model.Employee;
import com.coding.domain.model.Role;
import com.coding.domain.model.User;
import com.coding.repository.RoleRepository;
import com.coding.repository.UserRepository;
import com.coding.security.SecurityUtils;
import com.coding.service.MailService;
import com.coding.service.S3Service;
import com.coding.service.UserService;
import com.coding.web.request.RegisUserRequest;
import com.coding.web.request.UpdateProfileRequest;
import com.coding.web.response.UserProfileResponse;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final AppProperties appProperties;
    private final S3Service s3Service;
    private static final String USER_UPLOAD_PATH = "users";
    private final MessageSource messageSource;

    public UserServiceImpl(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final RoleRepository roleRepository,
            final MailService mailService,
            final AppProperties appProperties,
            S3Service s3Service, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
        this.appProperties = appProperties;
        this.s3Service = s3Service;
        this.messageSource = messageSource;
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
        final User user = new User();
        user.setUsername(request.username());
        String randomPassword = RandomUtil.randomAlphabetic(8);
        user.setPassword(passwordEncoder.encode(randomPassword));
        user.setEmail(request.email());
        user.setActivated(false);
        user.setActivationKey(RandomUtil.randomAlphabetic(32));
        user.setActivationDate(LocalDateTime.now());
        final Set<Role> roles = request.roles().stream()
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
        final Employee employee = new Employee();
        employee.setUser(user);
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        user.setEmployee(employee);
        userRepository.save(user);
        String url = appProperties.ui_url();
        String verifyUrl = url + "/" + "activate" + "/" + user.getActivationKey();
        final Map<String, Object> variables
                = ActivationUser.variables(user.getUsername(), randomPassword, verifyUrl);
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
        LocalDateTime activationDate = existingUser.getActivationDate();
        if (activationDate != null && checkVerificationTime(activationDate)) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    @Override
    public boolean activateRegistration(String key) {
        Preconditions.checkArgument(key != null, "key must not be null");
        return userRepository.findByActivationKey(key)
                .map(this::activateUser)
                .orElse(false);
    }

    @Override
    public UserProfileResponse findUserProfile() {
        return SecurityUtils.extractUsername()
                .map(userRepository::findOneByUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .stream()
                .peek(userProfileResponse -> {
                    String imageUrl = userProfileResponse.getImageUrl();
                    if (imageUrl != null) {
                        String preSignedUrl = s3Service.getPreSignedUrl(imageUrl).join();
                        if (preSignedUrl == null) {
                            userRepository.findByUsername(userProfileResponse.getUsername())
                                    .ifPresent(user -> {
                                        Employee employee = user.getEmployee();
                                        if (employee != null) {
                                            employee.setImageUrl(null);
                                            userRepository.save(user);
                                        }
                                    });
                        }
                        userProfileResponse.setImageUrl(preSignedUrl);
                    }
                })
                .findFirst()
                .orElseThrow(DataNotFoundException::new);
    }

    @Async
    public CompletableFuture<UserProfileResponse> findUserProfileAsync() {
        return SecurityUtils.extractUsernameAsync()
                .thenCompose(optionUsername -> optionUsername
                        .map(userRepository::findOneByUsername)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(userProfile -> {
                            final String imageUrl = userProfile.getImageUrl();
                            if (imageUrl != null) {
                                s3Service.getPreSignedUrl(imageUrl).thenAccept(preSignedUrl -> {
                                    if (preSignedUrl == null) {
                                        userRepository.findByUsername(userProfile.getUsername())
                                                .ifPresent(user -> {
                                                    Employee employee = user.getEmployee();
                                                    if (employee != null) {
                                                        employee.setImageUrl(null);
                                                        userRepository.save(user);
                                                    }
                                                });
                                    }
                                    userProfile.setImageUrl(preSignedUrl);
                                });
                            }
                            return CompletableFuture.completedFuture(userProfile);
                        })
                        .orElseThrow(DataNotFoundException::new));

    }

    @Override
    public boolean updateProfile(UpdateProfileRequest request) {
        Preconditions.checkArgument(request != null, "request must not be null");
        return SecurityUtils.extractUsername()
                .flatMap(userRepository::findByUsername)
                .map(user -> {
                    userRepository.findByEmail(request.email())
                            .ifPresent(existingUser -> {
                                if (!Objects.equals(existingUser.getId(), user.getId())) {
                                    throw new EmailAlreadyUsedException(existingUser.getEmail());
                                }
                            });
                    user.setEmail(request.email());
                    Employee employee = user.getEmployee();
                    if (employee != null) {
                        employee.setFirstName(request.firstName());
                        employee.setLastName(request.lastName());
                    } else {
                        employee = new Employee();
                        employee.setUser(user);
                        employee.setFirstName(request.firstName());
                        employee.setLastName(request.lastName());
                        user.setEmployee(employee);
                    }
                    userRepository.save(user);
                    return true;
                })
                .orElseThrow(DataNotFoundException::new);
    }

    private boolean activateUser(User user) {
        boolean isValidVerificationTime = checkVerificationTime(user.getActivationDate());
        if (!isValidVerificationTime) {
            userRepository.delete(user);
            userRepository.flush();
            log.error("Account verification time has expired");
            throw new TimeVerificationOutException("Account verification time has expired");
        }
        user.setActivated(true);
        user.setActivationKey(null);
        user.setActivationDate(null);
        userRepository.save(user);
        return true;
    }

    private boolean checkVerificationTime(LocalDateTime activationDate) {
        // Time valid is 3 hours
        return activationDate.plusHours(3).isAfter(LocalDateTime.now());
    }

    @Override
    public CompletableFuture<Boolean> updatePhoto(MultipartFile file) {
        Preconditions.checkArgument(file != null, "file must not be null");
        return SecurityUtils.extractUsername()
                .flatMap(userRepository::findByUsername)
                .map(user -> {
                    final String extension = FileUtils.getExtension(Objects.requireNonNull(file.getOriginalFilename()));
                    final String fileName = FileUtils.generateFileNameWithDateTime(user.getUsername()) + extension;
                    final String uploadPath = FileUtils.constructPath(USER_UPLOAD_PATH, user.getUsername(), fileName);
                    return s3Service
                            .cleanDirectory(FileUtils.constructPath(USER_UPLOAD_PATH, user.getUsername()))
                            .thenCompose(unused -> s3Service.uploadFile(uploadPath, file))
                            .thenAccept(unused -> {
                                Employee employee = getOrCreateEmployee(user.getEmployee());
                                employee.setImageUrl(uploadPath);
                                user.setEmployee(employee);
                                userRepository.save(user);
                            })
                            .handle((unused, ex) -> {
                                if (ex != null) {
                                    log.error("update photo failed", ex);
                                    return false;
                                }
                                return true;
                            });
                })
                .orElseThrow(DataNotFoundException::new);
    }

    private Employee getOrCreateEmployee(Employee employee) {
        return employee == null ? new Employee() : employee;
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword) {
        if (Objects.equals(oldPassword, newPassword)) {
            return false;
        } else {
            return SecurityUtils.extractUsername()
                    .map(userRepository::findByUsername)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(user -> updatePassword(user, oldPassword, newPassword))
                    .orElseThrow(DataNotFoundException::new);
        }
    }

    private boolean updatePassword(final User user, final String oldPassword, final String newPassword) {
        boolean isValidPassword = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isValidPassword) {
            String errorMessage = messageSource.getMessage(MessageKeyConstant.PASSWORD_INCORRECT, null, Locale.getDefault());
            throw new InvalidPasswordException(errorMessage);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
