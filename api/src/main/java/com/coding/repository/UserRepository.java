package com.coding.repository;

import com.coding.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

import static com.coding.core.constant.JpaConstant.USER_LIST;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @Override
    @EntityGraph(value =USER_LIST)
    List<User> findAll();

    @EntityGraph(attributePaths = {"roles", "employee"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles", "employee"})
    Optional<User> findByEmail(String email);
}
