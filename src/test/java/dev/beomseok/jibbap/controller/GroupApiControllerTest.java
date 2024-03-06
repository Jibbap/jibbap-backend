package dev.beomseok.jibbap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.beomseok.jibbap.config.H2Test;
import dev.beomseok.jibbap.dto.GroupInfo;
import dev.beomseok.jibbap.dto.GroupRequest;
import dev.beomseok.jibbap.dto.MealInfoInGroup;
import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.GroupRepository;
import dev.beomseok.jibbap.repository.MealRepository;
import dev.beomseok.jibbap.repository.RelationshipRepository;
import dev.beomseok.jibbap.repository.UserRepository;
import dev.beomseok.jibbap.util.DataSet;
import jakarta.transaction.Transactional;
import jdk.jfr.ContentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
class GroupApiControllerTest extends H2Test {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private MealRepository mealRepository;


    @Component
    @Data
    static class JoinGroupRequestBody{
        private String username_in_group;
    }

    @Component
    @Data
    static class UpdateGroupNameRequestBody{
        private String group_name;
    }

    @Component
    @Data
    static class UpdateUsernameInGroupRequestBody{
        private String username_in_group;
    }

    @BeforeAll
    public static void initData(@Autowired DataSet dataSet){
        dataSet.insert();
    }

    @AfterAll
    public static void deleteData(@Autowired DataSet dataSet){
        dataSet.delete();
    }

    private final String BASE_URL = "http://localhost:8080/api/groups";

    private String getUrl(String url){
        return new StringBuilder(BASE_URL).append(url).toString();
    }

    @Test
    void createGroup() throws Exception {
        String KAKAO_ID = "cg kakao id";
        String USERNAME = "cg username";
        String GROUP_NAME = "cg groupname";
        String USERNAME_IN_GROUP = "cg username in group";
        String UUID = "cg uuid";

        UserEntity user = new UserEntity();
        user.setKakaoId(KAKAO_ID);
        user.setUsername(USERNAME);
        userRepository.save(user);

        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setGroupName(GROUP_NAME);
        groupRequest.setUsernameInGroup(USERNAME_IN_GROUP);
        groupRequest.setKakaoId(KAKAO_ID);
        groupRequest.setUuid(UUID);

        String json = objectMapper.writeValueAsString(groupRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post(getUrl(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        assertEquals(GROUP_NAME,groupRepository.findByUuid(UUID).getGroupName());

    }

    @Test
    void getMealInfoInGroup() throws Exception {
        String UUID = "uuid1";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{uuid}/meal-info"),UUID)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        MealInfoInGroup mealInfoInGroup = objectMapper.readValue(result.getResponse().getContentAsString(),MealInfoInGroup.class);

        assertEquals("group1",mealInfoInGroup.getGroupName());
        assertEquals("000",mealInfoInGroup.getUserInfos().get(0).getIsJibbap());
    }

    @Test
    void getGroupInfoSuccess() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{uuid}/user-info"),"uuid1")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        GroupInfo groupInfo = objectMapper.readValue(result.getResponse().getContentAsString(),GroupInfo.class);
        assertEquals("group1",groupInfo.getGroupName());
        assertEquals(2,groupInfo.getUserInfos().size());
    }

    @Test
    void getGroupInfoFail() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{uuid}/user-info"),"fail uuid")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andReturn();
    }

    @Test
    void updateGroupName() throws Exception {
        String CHANGED_GROUP_NAME = "changed Group name";
        String UUID = "uuid2";

        GroupApiControllerTest.UpdateGroupNameRequestBody requestBody = new UpdateGroupNameRequestBody();
        requestBody.setGroup_name(CHANGED_GROUP_NAME);
        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(
                MockMvcRequestBuilders.put(getUrl("/{uuid}"),UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        assertEquals(CHANGED_GROUP_NAME, groupRepository.findByUuid(UUID).getGroupName());
    }

    @Transactional
    @Test
    void updateUsernameInGroup() throws Exception {
        String CHANGED_USERNAME_IN_GROUP="changed username";
        String UUID = "uuid2";
        String KAKAO_ID = "3333";

        GroupApiControllerTest.UpdateUsernameInGroupRequestBody requestBody = new GroupApiControllerTest.UpdateUsernameInGroupRequestBody();
        requestBody.setUsername_in_group(CHANGED_USERNAME_IN_GROUP);
        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(
                MockMvcRequestBuilders.put(getUrl("/{uuid}/users/{kakao_id}"),UUID,KAKAO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andReturn();

        RelationshipEntity relationship = userRepository.findByKakaoId(KAKAO_ID).getRelationshipEntities()
                .stream()
                .filter(r->r.getGroupEntity().getUuid()==UUID)
                .collect(Collectors.toList())
                .get(0);


        assertEquals(CHANGED_USERNAME_IN_GROUP,relationship.getUsernameInGroup());
    }

    @Transactional
    @Test
    void joinGroupSuccess() throws Exception {
        String USERNAME_IN_GROUP = "relationship5";
        String UUID = "uuid1";
        String KAKAO_ID = "3333";

        GroupApiControllerTest.JoinGroupRequestBody requestBody = new GroupApiControllerTest.JoinGroupRequestBody();
        requestBody.setUsername_in_group(USERNAME_IN_GROUP);
        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(
                MockMvcRequestBuilders.post(getUrl("/{uuid}/users/{kakao_id}"),UUID,KAKAO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        RelationshipEntity relationship = userRepository.findByKakaoId(KAKAO_ID).getRelationshipEntities()
                .stream()
                .filter(r->r.getGroupEntity().getUuid()==UUID)
                .collect(Collectors.toList())
                .get(0);

        assertEquals(USERNAME_IN_GROUP,relationship.getUsernameInGroup());
    }

    @Transactional
    @Test
    void joinGroupFail() throws Exception {
        String USERNAME_IN_GROUP = "fail relationship";
        String UUID = "fail uuid";
        String KAKAO_ID = "3333";

        GroupApiControllerTest.JoinGroupRequestBody requestBody = new GroupApiControllerTest.JoinGroupRequestBody();
        requestBody.setUsername_in_group(USERNAME_IN_GROUP);
        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(
                MockMvcRequestBuilders.post(getUrl("/{uuid}/users/{kakao_id}"),UUID,KAKAO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        List<RelationshipEntity> relationships = userRepository.findByKakaoId(KAKAO_ID).getRelationshipEntities()
                .stream()
                .filter(r->r.getGroupEntity().getUuid()==UUID)
                .collect(Collectors.toList());

        assertEquals(0, relationships.size());
    }
}