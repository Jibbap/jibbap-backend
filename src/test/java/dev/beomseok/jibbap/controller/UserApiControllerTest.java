package dev.beomseok.jibbap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.beomseok.jibbap.config.H2Test;
import dev.beomseok.jibbap.dto.GroupsIncludingUser;
import dev.beomseok.jibbap.dto.UserRequest;
import dev.beomseok.jibbap.entity.GroupEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.entity.UserEntity;
import dev.beomseok.jibbap.repository.GroupRepository;
import dev.beomseok.jibbap.repository.RelationshipRepository;
import dev.beomseok.jibbap.repository.UserRepository;
import dev.beomseok.jibbap.util.DataSet;
import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
class UserApiControllerTest extends H2Test {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void initData(@Autowired DataSet dataSet){
        dataSet.insert();
    }

    @AfterAll
    public static void deleteData(@Autowired DataSet dataSet){
        dataSet.delete();
    }

    private final String BASE_URL = "http://localhost:8080/api/users";

    private String getUrl(String url){
        return new StringBuilder(BASE_URL).append(url).toString();
    }


    @Test
    void createUser() throws Exception {
        String KAKAO_ID = "6666";
        String USERNAME = "user6";
        String PROFILE_IMAGE_URL = "url6";

        UserRequest userRequest = new UserRequest();
        userRequest.setKakaoId(KAKAO_ID);
        userRequest.setUsername(USERNAME);
        userRequest.setProfileImageUrl(PROFILE_IMAGE_URL);
        String json = new ObjectMapper().writeValueAsString(userRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post(getUrl(""))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        assertEquals(USERNAME,userRepository.findByKakaoId(KAKAO_ID).getUsername());
    }

    @Test
    void checkUserIsExisted() throws  Exception{
        String KAKAO_ID = "1111";

        mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{kakaoId}"),KAKAO_ID)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();
    }

    @Test
    void checkUserIsNotExisted() throws  Exception{
        String KAKAO_ID = "1234";

        mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{kakaoId}"),KAKAO_ID)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andReturn();
    }

    @Transactional
    @Test
    void getAllGroupsIncludingUser() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{kakao_id}/detail"),"1111")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        List<GroupsIncludingUser> groups = objectMapper.readerForListOf(GroupsIncludingUser.class).readValue(result.getResponse().getContentAsString());
        assertEquals(2,groups.size());
    }

    @Transactional
    @Test
    void getEmptyGroup() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(getUrl("/{kakao_id}/detail"),"asdf")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        List<GroupsIncludingUser> groups = objectMapper.readerForListOf(GroupsIncludingUser.class).readValue(result.getResponse().getContentAsString());
        assertEquals(0,groups.size());
    }
}