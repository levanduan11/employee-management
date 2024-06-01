package com.coding.repository.impl;

import com.coding.domain.model.*;
import com.coding.repository.UserQuerydslRepository;
import com.coding.web.response.UserProfileResponse;
import com.coding.web.response.UserResponse;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends AbstractQuerydslBaseRepository implements UserQuerydslRepository {
    private final QUser qUser = QUser.user;
    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Page<UserResponse> findAll(final Predicate predicate, final Pageable pageable) {
        final long offset = pageable.getOffset();
        final int pageSize = pageable.getPageSize();
        final Sort sort = pageable.getSort();
        OrderSpecifier<?>[] order = getOrder(sort, new PathBuilder<>(User.class, "user"));
        final JPAQuery<UserResponse> listQuery = query.select(Projections.constructor(
                        UserResponse.class,
                        qUser.username,
                        qUser.email,
                        qUser.employee.imageUrl,
                        qUser.activated,
                        qUser.createdDate))
                .from(qUser)
                .leftJoin(qUser.employee)
                .where(predicate)
                .offset(offset)
                .limit(pageSize)
                .orderBy(order);
        final JPAQuery<Long> countQuery = query
                .select(qUser.count())
                .from(qUser)
                .where(predicate);
        final List<UserResponse> fetch = listQuery.fetch();
        final long totalCount = countQuery.fetchFirst();
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    @Override
    public Page<UserResponse> findAllWithEmployee(final Predicate predicate, final Pageable pageable) {
        final JPAQuery<UserResponse> listQuery = query.select(Projections.constructor(
                        UserResponse.class,
                        qUser.username,
                        qUser.email,
                        qUser.employee.imageUrl,
                        qUser.activated,
                        qUser.createdDate))
                .from(qUser)
                .leftJoin(qUser.employee)
                .where(predicate);
        final Querydsl querydsl = querydsl(UserResponse.class);
        return fetch(querydsl, listQuery, pageable);
    }

    @Override
    public Optional<UserProfileResponse> findOneByUsername(String username) {
        final JPAQuery<User> u = query
                .selectFrom(qUser)
                .leftJoin(qUser.employee).fetchJoin()
                .leftJoin(qUser.roles).fetchJoin()
                .where(qUser.username.eq(username));
        User user = u.fetchOne();
        if (user == null) {
            return Optional.empty();
        } else {
            Set<Role> roles = user.getRoles();
            Employee employee = user.getEmployee();
            UserProfileResponse response = new UserProfileResponse();
            if (roles != null) {
                response.setRoles(roles.stream().map(Role::getName).map(String::valueOf).collect(Collectors.toSet()));
            }
            if (employee != null) {
                response.setFirstName(employee.getFirstName());
                response.setLastName(employee.getLastName());
                response.setImageUrl(employee.getImageUrl());
            }
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());
            return Optional.of(response);
        }
    }

}
