package dev.beomseok.jibbap.repository.group;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.beomseok.jibbap.dto.GroupDetail;

import java.util.List;

import static dev.beomseok.jibbap.entity.QGroupEntity.groupEntity;
import static dev.beomseok.jibbap.entity.QRelationshipEntity.relationshipEntity;
import static dev.beomseok.jibbap.entity.QUserEntity.userEntity;

public class GroupCustomRepositoryImpl implements GroupCustomRepository {

    private final JPAQueryFactory queryFactory;

    public GroupCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<GroupDetail> findGroupDetailByKakaoId(String kakaoId) {
        return queryFactory.select(Projections.constructor(GroupDetail.class,
                        groupEntity.groupName,
                        groupEntity.uuid,
                        relationshipEntity.usernameInGroup
                ))
                .from(relationshipEntity)
                .join(relationshipEntity.groupEntity, groupEntity)
                .join(relationshipEntity.userEntity, userEntity)
                .where(userEntity.kakaoId.eq(kakaoId))
                .fetch();
    }
}
