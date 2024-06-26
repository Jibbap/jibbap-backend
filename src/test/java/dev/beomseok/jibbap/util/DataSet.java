package dev.beomseok.jibbap.util;

import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.group.GroupRepository;
import dev.beomseok.jibbap.repository.meal.MealRepository;
import dev.beomseok.jibbap.repository.relationship.RelationshipRepository;
import dev.beomseok.jibbap.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSet {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RelationshipRepository relationshipRepository;
    private final MealRepository mealRepository;

    public void insert(){
        UserEntity user1 = createUserEntity("1111","url1","user1");
        UserEntity user2 = createUserEntity("2222","url2","user2");
        UserEntity user3 = createUserEntity("3333","url3","user3");
        UserEntity user4 = createUserEntity("4444","url4","user4");
        UserEntity user5 = createUserEntity("5555","url5","user5");
        UserEntity user6 = createUserEntity("asdf","url5","no group user");

        GroupEntity group1 = createGroupEntity("group1","uuid1");
        GroupEntity group2 = createGroupEntity("group2","uuid2");
        GroupEntity group3 = createGroupEntity("group3","uuid3");

        RelationshipEntity relationship1 = createRelationshipEntity("relationship1",user1,group1);
        RelationshipEntity relationship2 = createRelationshipEntity("relationship2",user1,group2);
        RelationshipEntity relationship3 = createRelationshipEntity("relationship3",user2,group1);
        RelationshipEntity relationship4 = createRelationshipEntity("relationship4",user3,group2);
        RelationshipEntity relationship5 = createRelationshipEntity("relationship5",user4,group2);
        RelationshipEntity relationship6 = createRelationshipEntity("relationship6",user5,group3);

        MealEntity meal1 = createMealEntity(LocalDate.now(),"000",relationship1);
        MealEntity meal2 = createMealEntity(LocalDate.now(),"222",relationship2);
        MealEntity meal3 = createMealEntity(LocalDate.now(),"111",relationship3);
        MealEntity meal4 = createMealEntity(LocalDate.now(),"012",relationship4);
        MealEntity meal5 = createMealEntity(LocalDate.now(),"210",relationship5);

        user1.addRelationships(relationship1,relationship2);
        user2.addRelationships(relationship3);
        user3.addRelationships(relationship4);
        user4.addRelationships(relationship5);
        user5.addRelationships(relationship6);

        group1.addRelationships(relationship1,relationship3);
        group2.addRelationships(relationship2,relationship4,relationship5);

        relationship1.addMeals(meal1);
        relationship2.addMeals(meal2);
        relationship3.addMeals(meal3);
        relationship4.addMeals(meal4);
        relationship5.addMeals(meal5);
    }

    public void delete(){
        mealRepository.deleteAllInBatch();
        relationshipRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        groupRepository.deleteAllInBatch();
    }

    private UserEntity createUserEntity(String kakao_id,String profileImageUrl,String username){
        UserEntity user = new UserEntity();
        user.setKakaoId(kakao_id);
        user.setProfileImageUrl(profileImageUrl);
        user.setUsername(username);
        return userRepository.save(user);
    }

    private GroupEntity createGroupEntity(String groupName, String uuid){
        GroupEntity group = new GroupEntity();
        group.setGroupName(groupName);
        group.setUuid(uuid);
        return groupRepository.save(group);
    }

    private RelationshipEntity createRelationshipEntity(String username, UserEntity userEntity, GroupEntity groupEntity){
        RelationshipEntity relationship = new RelationshipEntity();
        relationship.setUsernameInGroup(username);
        relationship.setUserEntity(userEntity);
        relationship.setGroupEntity(groupEntity);

        return relationshipRepository.save(relationship);
    }

    private MealEntity createMealEntity(LocalDate date, String isJibbap, RelationshipEntity relationship){
        MealEntity meal = new MealEntity();
        meal.setDate(date);
        meal.setIsJibbap(isJibbap);
        meal.setRelationshipEntity(relationship);

        return mealRepository.save(meal);
    }
}
