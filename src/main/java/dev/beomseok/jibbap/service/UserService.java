package dev.beomseok.jibbap.service;

import dev.beomseok.jibbap.dto.GroupDetail;
import dev.beomseok.jibbap.dto.GroupsIncludingUser;
import dev.beomseok.jibbap.dto.UserRequest;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.group.GroupRepository;
import dev.beomseok.jibbap.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Transactional(readOnly = true)
    public boolean checkUserIsExisted(String kakaoId){
        UserEntity user = userRepository.findByKakaoId(kakaoId);

        return user!=null;
    }

    @Transactional(readOnly = true)
    public List<GroupsIncludingUser> readAllGroupsIncludingUser(String kakaoId){
        List<GroupDetail> groupDetails= groupRepository.findGroupDetailByKakaoId(kakaoId);
        List<GroupsIncludingUser> userIncludedGroups = groupDetails
                .stream()
                .map(group -> {
                    GroupsIncludingUser eachGroup = new GroupsIncludingUser();
                    eachGroup.setGroupName(group.getGroupName());
                    eachGroup.setUuid(group.getUuid());
                    eachGroup.setUsernameInGroup(group.getUsernameInGroup());
                    return eachGroup;
                })
                .collect(Collectors.toList());
        return userIncludedGroups;
    }


}
