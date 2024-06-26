package dev.beomseok.jibbap.repository.relationship;

import dev.beomseok.jibbap.entity.RelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<RelationshipEntity,Long> {
    @Query(value = "select r.id, r.username_in_group, r.user_id, r.group_id " +
            "from relationship r " +
            "left join jibbap_user u " +
            "on r.user_id = u.id " +
            "left join jibbap_group g " +
            "on r.group_id = g.id " +
            "where g.uuid=:uuid and u.kakao_id=:kakaoId",nativeQuery = true)
    RelationshipEntity findByUuidAndKakaoId(
            @Param("uuid") String uuid,
            @Param("kakaoId") String kakaoId);
}
