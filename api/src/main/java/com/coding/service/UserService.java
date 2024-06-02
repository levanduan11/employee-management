package com.coding.service;

import com.coding.web.request.RegisUserRequest;
import com.coding.web.request.UpdateProfileRequest;
import com.coding.web.response.UserProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface UserService {
    Long register(RegisUserRequest request);

    boolean activateRegistration(String key);

    UserProfileResponse findUserProfile();

    CompletableFuture<UserProfileResponse> findUserProfileAsync();

    boolean updateProfile(UpdateProfileRequest request);

    CompletableFuture<Boolean> updatePhoto(MultipartFile file);
}
