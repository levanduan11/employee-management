package com.coding.domain.model;

import jakarta.persistence.*;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditable<ID extends Serializable> extends AbstractPersistable<ID> implements Serializable, Auditable<Long, ID, LocalDateTime> {
    @Serial
    private static final long serialVersionUID = 141481953116476081L;
    @JoinColumn(name = "created_by")
    private @Nullable Long createdBy;

    @Column(name = "created_date")
    private @Nullable LocalDateTime createdDate;

    @JoinColumn(name = "last_modified_by")
    private @Nullable Long lastModifiedBy;

    @Column(name = "last_modified_date")
    private @Nullable LocalDateTime lastModifiedDate;

    @Override
    public void setCreatedBy(@Nullable Long createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<Long> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public void setCreatedDate(@Nullable LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    @Override
    public void setLastModifiedBy(@Nullable Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Optional<Long> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @Override
    public void setLastModifiedDate(@Nullable LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }
}
