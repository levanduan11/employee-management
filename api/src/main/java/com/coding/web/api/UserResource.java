package com.coding.web.api;

import com.coding.service.UserService;
import com.coding.web.ApiResponse;
import com.coding.web.request.RegisUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisUserRequest request) throws URISyntaxException {
        Long id = userService.register(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.Status.TRUE)
                .message("register success")
                .data(id)
                .build();
        return ResponseEntity.created(new URI("/api/v1/users/" + id)).body(apiResponse);
    }
}
