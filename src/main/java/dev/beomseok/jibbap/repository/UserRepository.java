package dev.beomseok.jibbap.repository;

import dev.beomseok.jibbap.dto.GroupDetail;
import dev.beomseok.jibbap.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByKakaoId(String kakao_id);
}
