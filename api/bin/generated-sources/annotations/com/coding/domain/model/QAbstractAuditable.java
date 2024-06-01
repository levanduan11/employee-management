package com.coding.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractAuditable is a Querydsl query type for AbstractAuditable
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractAuditable extends EntityPathBase<AbstractAuditable<? extends java.io.Serializable>> {

    private static final long serialVersionUID = 591774523L;

    public static final QAbstractAuditable abstractAuditable = new QAbstractAuditable("abstractAuditable");

    public final QAbstractPersistable _super = new QAbstractPersistable(this);

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    //inherited
    public final SimplePath<java.io.Serializable> id = _super.id;

    public final NumberPath<Long> lastModifiedBy = createNumber("lastModifiedBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractAuditable(String variable) {
        super((Class) AbstractAuditable.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractAuditable(Path<? extends AbstractAuditable> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractAuditable(PathMetadata metadata) {
        super((Class) AbstractAuditable.class, metadata);
    }

}

