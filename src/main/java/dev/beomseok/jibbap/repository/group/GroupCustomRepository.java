package dev.beomseok.jibbap.repository.group;

import dev.beomseok.jibbap.dto.GroupDetail;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupCustomRepository {
    List<GroupDetail> findGroupDetailByKakaoId(@Param("kakaoId") String kakaoId);
}
