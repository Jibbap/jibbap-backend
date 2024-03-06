package dev.beomseok.jibbap.repository;

import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MealRepository extends JpaRepository<MealEntity,Long> {

    @Query(value = "select * " +
            "from meal m " +
            "where m.relationship_id = :relationshipId and m.date = :date",nativeQuery = true)
    MealEntity findByRelationshipIdAndDate(
            @Param("relationshipId") Long relationship_id,
            @Param("date") LocalDate date);
}
