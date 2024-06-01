package com.coding.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuerydslBaseRepository<T> {
    Page<T> findAll(Predicate predicate, Pageable pageable);
}
