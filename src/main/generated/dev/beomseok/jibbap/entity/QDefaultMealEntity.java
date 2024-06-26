package dev.beomseok.jibbap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultMealEntity is a Querydsl query type for DefaultMealEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDefaultMealEntity extends EntityPathBase<DefaultMealEntity> {

    private static final long serialVersionUID = -1509945002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultMealEntity defaultMealEntity = new QDefaultMealEntity("defaultMealEntity");

    public final EnumPath<dev.beomseok.jibbap.entity.enums.Duration> duration = createEnum("duration", dev.beomseok.jibbap.entity.enums.Duration.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isJibbap = createBoolean("isJibbap");

    public final QRelationshipEntity relationshipEntity;

    public final EnumPath<dev.beomseok.jibbap.entity.enums.WhichMeal> which = createEnum("which", dev.beomseok.jibbap.entity.enums.WhichMeal.class);

    public QDefaultMealEntity(String variable) {
        this(DefaultMealEntity.class, forVariable(variable), INITS);
    }

    public QDefaultMealEntity(Path<? extends DefaultMealEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultMealEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefaultMealEntity(PathMetadata metadata, PathInits inits) {
        this(DefaultMealEntity.class, metadata, inits);
    }

    public QDefaultMealEntity(Class<? extends DefaultMealEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.relationshipEntity = inits.isInitialized("relationshipEntity") ? new QRelationshipEntity(forProperty("relationshipEntity"), inits.get("relationshipEntity")) : null;
    }

}

