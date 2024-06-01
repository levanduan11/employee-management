package com.coding.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;

public abstract class AbstractQuerydslBaseRepository{
    protected final EntityManager entityManager;
    protected final JPAQueryFactory query;

    protected AbstractQuerydslBaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.query = new JPAQueryFactory(entityManager);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <E> OrderSpecifier<?>[] getOrder(Sort sort, PathBuilder<E> pathBuilder) {
        return sort.stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    String property = order.getProperty();
                    return new OrderSpecifier(direction, pathBuilder.get(property));
                })
                .toArray(OrderSpecifier[]::new);
    }

    public <T> Querydsl querydsl(final Class<T> clazz) {
        PathBuilderFactory pathBuilderFactory = new PathBuilderFactory();
        PathBuilder<T> pathBuilder = pathBuilderFactory.create(clazz);
        return new Querydsl(entityManager, pathBuilder);
    }

    public <T> Page<T> fetch(final Querydsl querydsl, final JPAQuery<T> query, final Pageable pageable) {
        JPQLQuery<T> result = querydsl.applyPagination(pageable, query);
        return new PageImpl<>(result.fetch(), pageable, result.fetchCount());
    }
}
