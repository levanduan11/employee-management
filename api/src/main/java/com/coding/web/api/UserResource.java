package com.coding.web.api;

import com.coding.service.UserService;
import com.coding.web.ApiResponse;
import com.coding.web.request.RegisUserRequest;
import com.coding.web.request.UpdateProfileRequest;
import com.coding.web.response.UserProfileResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService) {
        this.userService = userService;
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

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody @Valid final UpdateProfileRequest request) {
        boolean result = userService.updateProfile(request);
        ApiResponse response = ApiResponse.builder()
                .status(ApiResponse.Status.TRUE)
                .data(result)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile/photo")
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
}
