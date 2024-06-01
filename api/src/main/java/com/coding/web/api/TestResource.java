package com.coding.web.api;

import com.coding.domain.model.QUser;
import com.coding.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public record TestResource(UserRepository userRepository) {

    @GetMapping
    public Page<?> test(Pageable pageable, TestParams params) {
        QUser qUser = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (params.email() != null && !params.email().isEmpty()) {
            booleanBuilder.and(qUser.email.eq(params.email()));
        }
        if (params.username() != null && !params.username().isEmpty()) {
            booleanBuilder.and(qUser.username.eq(params.username()));
        }
        return null;
    }
}

record TestParams(String username, String email) {
}
