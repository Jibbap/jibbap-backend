package dev.beomseok.jibbap.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "relationship")
public class RelationshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,name = "username_in_group")
    private String usernameInGroup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    private GroupEntity groupEntity;

    @OneToMany(mappedBy = "relationshipEntity")
    private List<MealEntity> mealEntities = new ArrayList<>();

    @OneToMany(mappedBy = "relationshipEntity")
    private List<DefaultMealEntity> defaultMealEntities = new ArrayList<>();

    public void addMeals(MealEntity... meals){
        Collections.addAll(this.mealEntities,meals);
    }
}
