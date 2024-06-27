package dev.beomseok.jibbap.service;

import dev.beomseok.jibbap.dto.MealRequest;
import dev.beomseok.jibbap.entity.MealEntity;
import dev.beomseok.jibbap.entity.RelationshipEntity;
import dev.beomseok.jibbap.repository.meal.MealRepository;
import dev.beomseok.jibbap.repository.relationship.RelationshipRepository;
import dev.beomseok.jibbap.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final RelationshipRepository relationshipRepository;

    public Boolean createMealInfo(String uuid, String kakaoId, MealRequest mealRequest){
        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(uuid,kakaoId);
        if(relationship==null || mealRequest.getDate()==null || mealRequest.getIsJibbap()==null || mealRequest.getIsJibbap().isEmpty()){
            return false;
        }

        MealEntity meal = new MealEntity();
        meal.setDate(mealRequest.getDate());
        meal.setIsJibbap(mealRequest.getIsJibbap());
        meal.setRelationshipEntity(relationship);
        mealRepository.save(meal);
        return true;
    }

    public Boolean updateMealInfo(String uuid, String kakaoId, MealRequest mealRequest){
        RelationshipEntity relationship = relationshipRepository.findByUuidAndKakaoId(uuid,kakaoId);
        if(relationship==null || mealRequest.getDate()==null || mealRequest.getIsJibbap()==null || mealRequest.getIsJibbap().isEmpty()){
            return false;
        }

        MealEntity meal = mealRepository.findByRelationshipIdAndDate(relationship.getId(),mealRequest.getDate());
        meal.setDate(mealRequest.getDate());
        meal.setIsJibbap(mealRequest.getIsJibbap());
        return true;
    }
}
