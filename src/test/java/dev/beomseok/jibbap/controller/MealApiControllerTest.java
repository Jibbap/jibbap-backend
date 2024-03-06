package dev.beomseok.jibbap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.beomseok.jibbap.config.H2Test;
import dev.beomseok.jibbap.dto.MealRequest;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.repository.MealRepository;
import dev.beomseok.jibbap.repository.RelationshipRepository;
import dev.beomseok.jibbap.repository.UserRepository;
import dev.beomseok.jibbap.util.DataSet;
import jakarta.transaction.Transactional;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
class MealApiControllerTest extends H2Test {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DataSet dataSet;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private MealRepository mealRepository;

    @BeforeAll
    public static void initData(@Autowired DataSet dataSet){
        dataSet.insert();
    }

    @AfterAll
    public static void deleteData(@Autowired DataSet dataSet){
        dataSet.delete();
    }

    private final String BASE_URL = "http://localhost:8080/api/meals";

    private String getUrl(String url){
        return new StringBuilder(BASE_URL).append(url).toString();
    }

    @Transactional
    @Test
    void createMealInfo() throws Exception {
        String UUID = "uuid3";
        String KAKAO_ID = "5555";
        String IS_JIBBAP = "012";

        MealRequest mealRequest = new MealRequest();
        mealRequest.setDate(LocalDate.now());
        mealRequest.setIsJibbap(IS_JIBBAP);
        String json = objectMapper.writeValueAsString(mealRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(getUrl("/groups/{uuid}/users/{kakaoId}"),UUID,KAKAO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andReturn();

        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(UUID,KAKAO_ID);
        MealEntity todayMeal = mealRepository.findByRelationshipIdAndDate(relationship.getId(),LocalDate.now());

        assertEquals(IS_JIBBAP,todayMeal.getIsJibbap());
    }

    @Transactional
    @Test
    void updateMealInfo() throws Exception {
        String UUID = "uuid1";
        String KAKAO_ID = "1111";
        String CHANGED_IS_JIBBAP = "002";

        MealRequest mealRequest = new MealRequest();
        mealRequest.setDate(LocalDate.now());
        mealRequest.setIsJibbap(CHANGED_IS_JIBBAP);
        String json = objectMapper.writeValueAsString(mealRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put(getUrl("/groups/{uuid}/users/{kakaoId}"),UUID,KAKAO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(UUID,KAKAO_ID);

        List<MealEntity> todayMeal = relationship.getMealEntities()
                .stream()
                .filter(m -> m.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());

        assertEquals(1,todayMeal.size());
        assertEquals(CHANGED_IS_JIBBAP,todayMeal.get(0).getIsJibbap());
    }
}