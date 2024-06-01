package com.coding.repository;


import com.coding.web.response.UserProfileResponse;
import com.coding.web.response.UserResponse;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserQuerydslRepository {
    Page<UserResponse> findAll(Predicate predicate, Pageable pageable);

    Page<UserResponse> findAllWithEmployee(Predicate predicate, Pageable pageable);
    Optional<UserProfileResponse>findOneByUsername(String username);
}
