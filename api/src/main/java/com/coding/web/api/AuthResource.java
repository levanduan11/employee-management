package com.coding.web.api;

import com.coding.service.AuthService;
import com.coding.web.ApiResponse;
import com.coding.web.request.LoginRequest;
import com.coding.web.response.JwtResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
public class AuthResource {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthService authService;

    public AuthResource(AuthenticationManagerBuilder authenticationManagerBuilder, AuthService authService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = authService.generateAccessToken(authentication, false);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        final var jwtResponse = new JwtResponse(jwt);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(ApiResponse.builder()
                        .status(ApiResponse.Status.TRUE)
                        .data(jwtResponse)
                        .build());
    }

}
