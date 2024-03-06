package dev.beomseok.jibbap.repository;

import dev.beomseok.jibbap.dto.GroupDetail;
import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    GroupEntity findByUuid(String uuid);

    @Query(value = "select g.group_name, g.uuid, r.username_in_group, g.id " +
            "from jibbap_group g " +
            "join relationship r " +
            "on r.group_id = g.id " +
            "join jibbap_user u " +
            "on r.user_id = u.id " +
            "where u.kakao_id=:kakaoId",nativeQuery = true)
    List<GroupDetail> findGroupDetailByKakaoId(@Param("kakaoId") String kakaoId);
}
