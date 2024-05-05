package com.coding.domain.model;

import jakarta.persistence.MappedSuperclass;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractPersistable<ID extends Serializable> implements Persistable<ID> {
    public abstract ID getId();

    public abstract void setId(ID id);

    @Override
    public boolean isNew() {
        return null == getId();
    }
}
