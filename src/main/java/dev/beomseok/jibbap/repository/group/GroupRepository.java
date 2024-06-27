package dev.beomseok.jibbap.repository.group;

import dev.beomseok.jibbap.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity,Long>,GroupCustomRepository {

    GroupEntity findByUuid(String uuid);
}
