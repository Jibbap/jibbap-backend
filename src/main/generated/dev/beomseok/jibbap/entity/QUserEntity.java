package dev.beomseok.jibbap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -1677043777L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakaoId = createString("kakaoId");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final ListPath<RelationshipEntity, QRelationshipEntity> relationshipEntities = this.<RelationshipEntity, QRelationshipEntity>createList("relationshipEntities", RelationshipEntity.class, QRelationshipEntity.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

