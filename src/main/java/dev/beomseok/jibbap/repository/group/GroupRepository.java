package dev.beomseok.jibbap.repository.group;

import dev.beomseok.jibbap.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity,Long>,GroupCustomRepository {

    GroupEntity findByUuid(String uuid);

//    @Query(value = "select g.group_name, g.uuid, r.username_in_group, g.id " +
//            "from jibbap_group g " +
//            "join relationship r " +
//            "on r.group_id = g.id " +
//            "join jibbap_user u " +
//            "on r.user_id = u.id " +
//            "where u.kakao_id=:kakaoId",nativeQuery = true)
//    List<GroupDetail> findGroupDetailByKakaoId(@Param("kakaoId") String kakaoId);
}
