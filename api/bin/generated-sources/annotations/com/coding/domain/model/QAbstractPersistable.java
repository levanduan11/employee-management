package com.coding.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractPersistable is a Querydsl query type for AbstractPersistable
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractPersistable extends EntityPathBase<AbstractPersistable<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -502893772L;

    public static final QAbstractPersistable abstractPersistable = new QAbstractPersistable("abstractPersistable");

    public final SimplePath<java.io.Serializable> id = createSimple("id", java.io.Serializable.class);

    public final BooleanPath new$ = createBoolean("new");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(String variable) {
        super((Class) AbstractPersistable.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(Path<? extends AbstractPersistable> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(PathMetadata metadata) {
        super((Class) AbstractPersistable.class, metadata);
    }

}

