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
@Table(name = "jibbap_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,name = "kakao_id")
    private String kakaoId;

    @Column(length = 20)
    private String username;

    @Column(length = 200,name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "userEntity")
    private List<RelationshipEntity> relationshipEntities = new ArrayList<>();

    public void addRelationships(RelationshipEntity... relationshipEntities){
        Collections.addAll(this.relationshipEntities,relationshipEntities);
    }
}
