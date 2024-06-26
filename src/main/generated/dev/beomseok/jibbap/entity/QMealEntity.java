package dev.beomseok.jibbap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMealEntity is a Querydsl query type for MealEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMealEntity extends EntityPathBase<MealEntity> {

    private static final long serialVersionUID = 18261431L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMealEntity mealEntity = new QMealEntity("mealEntity");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isJibbap = createString("isJibbap");

    public final QRelationshipEntity relationshipEntity;

    public QMealEntity(String variable) {
        this(MealEntity.class, forVariable(variable), INITS);
    }

    public QMealEntity(Path<? extends MealEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMealEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMealEntity(PathMetadata metadata, PathInits inits) {
        this(MealEntity.class, metadata, inits);
    }

    public QMealEntity(Class<? extends MealEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.relationshipEntity = inits.isInitialized("relationshipEntity") ? new QRelationshipEntity(forProperty("relationshipEntity"), inits.get("relationshipEntity")) : null;
    }

}

