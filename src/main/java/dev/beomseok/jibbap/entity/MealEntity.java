package dev.beomseok.jibbap.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name="meal")
public class MealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "is_jibbap",length = 3)
    private String isJibbap;

    @ManyToOne
    @JoinColumn(name = "relationship_id")
    private RelationshipEntity relationshipEntity;
}
