package dev.beomseok.jibbap.entity;

import dev.beomseok.jibbap.entity.enums.Duration;
import dev.beomseok.jibbap.entity.enums.WhichMeal;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "default_meal")
public class DefaultMealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Duration duration;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private WhichMeal which;

    @Column(name = "is_jibbap")
    private Boolean isJibbap;

    @ManyToOne
    @JoinColumn(name = "relationship_id")
    private RelationshipEntity relationshipEntity;
}
