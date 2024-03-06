package dev.beomseok.jibbap.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "jibbap_group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,name = "group_name")
    private String groupName;

    @Column(length = 40)
    private String uuid;

    @OneToMany(mappedBy = "groupEntity")
    private List<RelationshipEntity> relationshipEntities = new ArrayList<>();

    public void addRelationships(RelationshipEntity... relationshipEntities){
        Collections.addAll(this.relationshipEntities,relationshipEntities);
    }
}
