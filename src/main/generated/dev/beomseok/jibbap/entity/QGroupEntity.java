package dev.beomseok.jibbap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupEntity is a Querydsl query type for GroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupEntity extends EntityPathBase<GroupEntity> {

    private static final long serialVersionUID = 1797968177L;

    public static final QGroupEntity groupEntity = new QGroupEntity("groupEntity");

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RelationshipEntity, QRelationshipEntity> relationshipEntities = this.<RelationshipEntity, QRelationshipEntity>createList("relationshipEntities", RelationshipEntity.class, QRelationshipEntity.class, PathInits.DIRECT2);

    public final StringPath uuid = createString("uuid");

    public QGroupEntity(String variable) {
        super(GroupEntity.class, forVariable(variable));
    }

    public QGroupEntity(Path<? extends GroupEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupEntity(PathMetadata metadata) {
        super(GroupEntity.class, metadata);
    }

}

