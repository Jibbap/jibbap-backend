package dev.beomseok.jibbap.service;

import dev.beomseok.jibbap.dto.MealRequest;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.repository.meal.MealRepository;
import dev.beomseok.jibbap.repository.relationship.RelationshipRepository;
import dev.beomseok.jibbap.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final RelationshipRepository relationshipRepository;

    public Boolean updateMealInfo(String uuid, String kakaoId, MealRequest mealRequest){
        Boolean isCreated = false;
        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(uuid,kakaoId);
        MealEntity meal = mealRepository.findByRelationshipIdAndDate(relationship.getId(),mealRequest.getDate());

        if(meal==null){
            meal = new MealEntity();
            meal.setDate(mealRequest.getDate());
            meal.setRelationshipEntity(relationship);
            isCreated = true;
        }
        meal.setIsJibbap(mealRequest.getIsJibbap());
        mealRepository.save(meal);

        if(isCreated){
            relationship.addMeals(meal);
        }

        return isCreated;
    }
}
