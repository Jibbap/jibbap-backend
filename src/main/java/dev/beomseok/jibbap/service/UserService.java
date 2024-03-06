package dev.beomseok.jibbap.service;

import dev.beomseok.jibbap.dto.GroupDetail;
import dev.beomseok.jibbap.dto.GroupsIncludingUser;
import dev.beomseok.jibbap.dto.UserRequest;
import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.GroupRepository;
import dev.beomseok.jibbap.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public void createUser(UserRequest user){
        if(userRepository.findByKakaoId(user.getKakaoId()) != null){
            return;
        }

        UserEntity newUser = new UserEntity();
        newUser.setKakaoId(user.getKakaoId());
        newUser.setProfileImageUrl(user.getProfileImageUrl());
        newUser.setUsername(user.getUsername());

        userRepository.save(newUser);
    }

    public boolean checkUserIsExisted(String kakaoId){
        UserEntity user = userRepository.findByKakaoId(kakaoId);

        return user!=null;
    }

    @Transactional
    public List<GroupsIncludingUser> readAllGroupsIncludingUser(String kakaoId){
        List<GroupDetail> groupDetails= groupRepository.findGroupDetailByKakaoId(kakaoId);
        List<GroupsIncludingUser> userIncludedGroups = groupDetails
                .stream()
                .map(group -> {
                    GroupsIncludingUser eachGroup = new GroupsIncludingUser();
                    eachGroup.setGroupName(group.getGroup_name());
                    eachGroup.setUuid(group.getUuid());
                    eachGroup.setUsernameInGroup(group.getUsername_in_group());
                    return eachGroup;
                })
                .collect(Collectors.toList());
        return userIncludedGroups;
    }


}
