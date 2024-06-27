package dev.beomseok.jibbap.repository.meal;

import dev.beomseok.jibbap.entity.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MealRepository extends JpaRepository<MealEntity,Long> {

    @Query(value = "select m " +
            "from MealEntity m " +
            "where m.relationshipEntity.id = :relationshipId and m.date = :date")
    MealEntity findByRelationshipIdAndDate(
            @Param("relationshipId") Long relationship_id,
            @Param("date") LocalDate date);
}
