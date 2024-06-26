package dev.beomseok.jibbap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRelationshipEntity is a Querydsl query type for RelationshipEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRelationshipEntity extends EntityPathBase<RelationshipEntity> {

    private static final long serialVersionUID = 1336466284L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRelationshipEntity relationshipEntity = new QRelationshipEntity("relationshipEntity");

    public final ListPath<DefaultMealEntity, QDefaultMealEntity> defaultMealEntities = this.<DefaultMealEntity, QDefaultMealEntity>createList("defaultMealEntities", DefaultMealEntity.class, QDefaultMealEntity.class, PathInits.DIRECT2);

    public final QGroupEntity groupEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MealEntity, QMealEntity> mealEntities = this.<MealEntity, QMealEntity>createList("mealEntities", MealEntity.class, QMealEntity.class, PathInits.DIRECT2);

    public final QUserEntity userEntity;

    public final StringPath usernameInGroup = createString("usernameInGroup");

    public QRelationshipEntity(String variable) {
        this(RelationshipEntity.class, forVariable(variable), INITS);
    }

    public QRelationshipEntity(Path<? extends RelationshipEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRelationshipEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRelationshipEntity(PathMetadata metadata, PathInits inits) {
        this(RelationshipEntity.class, metadata, inits);
    }

    public QRelationshipEntity(Class<? extends RelationshipEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupEntity = inits.isInitialized("groupEntity") ? new QGroupEntity(forProperty("groupEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

