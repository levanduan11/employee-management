package com.coding.repository;

import com.coding.domain.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, UserQuerydslRepository {

    @EntityGraph(attributePaths = {"roles", "employee"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles", "employee"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles", "employee"})
    Optional<User> findByActivationKey(String activationKey);

}
