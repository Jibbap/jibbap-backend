package dev.beomseok.jibbap.service;

import dev.beomseok.jibbap.dto.*;
import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.group.GroupRepository;
import dev.beomseok.jibbap.repository.meal.MealRepository;
import dev.beomseok.jibbap.repository.relationship.RelationshipRepository;
import dev.beomseok.jibbap.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;
    private final MealRepository mealRepository;

    public void CreateGroup(GroupRequest groupRequest){
        UserEntity user = userRepository.findByKakaoId(groupRequest.getKakaoId());
        GroupEntity group = new GroupEntity();
        group.setGroupName(groupRequest.getGroupName());
        group.setUuid(groupRequest.getUuid());
        groupRepository.save(group);

        RelationshipEntity relationship = new RelationshipEntity();
        relationship.setUsernameInGroup(groupRequest.getUsernameInGroup());
        relationship.setGroupEntity(group);
        relationship.setUserEntity(user);
        relationshipRepository.save(relationship);

        user.addRelationships(relationship);
        group.addRelationships(relationship);
        userRepository.save(user);
        groupRepository.save(group);
    }



    @Transactional(readOnly = true)
    public MealInfoInGroup readMealInfosInGroup(String uuid){ // test 아직 안 만듬
        GroupEntity group = groupRepository.findByUuid(uuid);
        if(group==null){
            return null;
        }

        List<UserInfoWithMealInGroup> userInfoWithMealInGroup = group.getRelationshipEntities()
                .stream()
                .map(relationship -> {
                    UserInfoWithMealInGroup newUserInfoWithMealInGroup = new UserInfoWithMealInGroup();
                    UserEntity user = relationship.getUserEntity();
                    newUserInfoWithMealInGroup.setUsernameInGroup(relationship.getUsernameInGroup());
                    newUserInfoWithMealInGroup.setProfileImageUrl(user.getProfileImageUrl());
                    newUserInfoWithMealInGroup.setKakaoId(user.getKakaoId());

                    MealEntity meal = mealRepository.findByRelationshipIdAndDate(relationship.getId(), LocalDate.now());
                    newUserInfoWithMealInGroup.setIsJibbap((meal == null) ? "222" : meal.getIsJibbap());

                    return newUserInfoWithMealInGroup;
                })
                .collect(Collectors.toList());

        MealInfoInGroup mealInfoInGroup = new MealInfoInGroup();
        mealInfoInGroup.setGroupName(group.getGroupName());
        mealInfoInGroup.setUserInfos(userInfoWithMealInGroup);
        return mealInfoInGroup;
    }

    @Transactional(readOnly = true)
    public GroupInfo readGroupInfo(String uuid){
        GroupEntity group = groupRepository.findByUuid(uuid);
        if(group==null){
            return null;
        }

        GroupInfo groupInfo = new GroupInfo();
        List<RelationshipEntity> relationships = group.getRelationshipEntities();

        List<UserInfoInGroup> userInfoInGroups = new ArrayList<>();
        relationships.forEach(relationship -> {
            UserInfoInGroup userInfoInGroup = new UserInfoInGroup();
            UserEntity user = relationship.getUserEntity();
            userInfoInGroup.setUsernameInGroup(relationship.getUsernameInGroup());
            userInfoInGroup.setKakaoId(user.getKakaoId());
            userInfoInGroup.setProfileImageUrl(user.getProfileImageUrl());
            userInfoInGroups.add(userInfoInGroup);
        });
        groupInfo.setGroupName(group.getGroupName());
        groupInfo.setUserInfos(userInfoInGroups);

        return groupInfo;
    }

    public void updateGroupName(String uuid, String groupName){
        GroupEntity group = groupRepository.findByUuid(uuid);
        group.setGroupName(groupName);
        groupRepository.save(group);
    }

    public boolean updateUsernameInGroup(String uuid, String kakaoId, String usernameInGroup){
        GroupEntity group = groupRepository.findByUuid(uuid);
        if(group==null){
            return false;
        }

        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(uuid,kakaoId);

        relationship.setUsernameInGroup(usernameInGroup);
        relationshipRepository.save(relationship);

        return true;
    }

    public boolean joinGroup(String uuid, String kakaoId, String usernameInGroup){
        GroupEntity group = groupRepository.findByUuid(uuid);
        if(group==null){
            return false;
        }

        UserEntity user = userRepository.findByKakaoId(kakaoId);
        RelationshipEntity relationship = new RelationshipEntity();
        relationship.setUsernameInGroup(usernameInGroup);
        relationship.setUserEntity(user);
        relationship.setGroupEntity(group);
        relationshipRepository.save(relationship);

        user.addRelationships(relationship);
        group.addRelationships(relationship);

        return true;
    }
}
