package com.coding.web.api;

import com.coding.core.exception.DataNotFoundException;
import com.coding.service.UserService;
import com.coding.web.ApiResponse;
import com.coding.web.request.RegisUserRequest;
import com.coding.web.request.UpdatePasswordRequest;
import com.coding.web.request.UpdateProfileRequest;
import com.coding.web.response.UserProfileResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final UserService userService;
    private final MessageSource messageSource;
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid final RegisUserRequest request) throws URISyntaxException {
        Long id = userService.register(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.Status.TRUE)
                .message("register success")
                .data(id)
                .build();
        return ResponseEntity.created(new URI("/api/v1/users/" + id)).body(apiResponse);
    }

    @GetMapping("/activate/{key}")
    public ResponseEntity<ApiResponse> activateRegistration(@PathVariable final String key) {
        boolean result = userService.activateRegistration(key);
        ApiResponse response = ApiResponse.builder()
                .status(ApiResponse.Status.TRUE)
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile() {
        UserProfileResponse userProfile = userService.findUserProfile();
        ApiResponse response = ApiResponse.builder()
                .status(ApiResponse.Status.TRUE)
                .data(userProfile)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/async")
    public DeferredResult<ResponseEntity<ApiResponse>> getUserProfileAsync() {
        DeferredResult<ResponseEntity<ApiResponse>> deferredResult = new DeferredResult<>();
        userService.findUserProfileAsync()
                .thenAccept(userProfile -> {
                    var response = ResponseEntity.ok(ApiResponse.builder()
                            .status(ApiResponse.Status.TRUE)
                            .data(userProfile)
                            .build());
                    deferredResult.setResult(response);
                })
                .exceptionally(e -> {
                    log.info("get user profile failed", e);
                    ResponseEntity<ApiResponse> error;
                    Throwable cause = e.getCause();
                    if (cause instanceof DataNotFoundException) {
                        error = ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse
                                        .builder()
                                        .status(ApiResponse.Status.FALSE)
                                        .message(cause.getMessage())
                                        .build());
                    } else {
                        error = ResponseEntity
                                .internalServerError()
                                .body(ApiResponse.builder()
                                        .status(ApiResponse.Status.FALSE)
                                        .message(messageSource.getMessage("error.500", null, Locale.getDefault()))
                                        .build());
                    }

                    deferredResult.setResult(error);
                    return null;
                });
        return deferredResult;
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody @Valid final UpdateProfileRequest request) {
        boolean result = userService.updateProfile(request);
        ApiResponse response = ApiResponse.builder()
                .status(ApiResponse.Status.TRUE)
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/photo")
    public DeferredResult<ResponseEntity<ApiResponse>> updatePhoto(MultipartFile file) {
        DeferredResult<ResponseEntity<ApiResponse>> deferredResult = new DeferredResult<>();
        userService.updatePhoto(file)
                .thenAccept(result -> {
                    var response = ResponseEntity.ok(ApiResponse.builder()
                            .status(ApiResponse.Status.TRUE)
                            .data(result)
                            .build());
                    deferredResult.setResult(response);
                })
                .exceptionally(e -> {
                    log.info("update photo failed", e);
                    var error = ResponseEntity
                            .internalServerError()
                            .body(ApiResponse.builder()
                                    .status(ApiResponse.Status.FALSE)
                                    .build());
                    deferredResult.setResult(error);
                    return null;
                });
        return deferredResult;
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody @Valid final UpdatePasswordRequest request) {
        boolean result = userService.updatePassword(request.getOldPassword(), request.getNewPassword());
        ApiResponse response = ApiResponse.builder()
                .status(ApiResponse.Status.TRUE)
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }
}
