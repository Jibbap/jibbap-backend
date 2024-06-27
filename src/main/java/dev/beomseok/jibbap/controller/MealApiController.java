package dev.beomseok.jibbap.controller;

import dev.beomseok.jibbap.dto.MealRequest;
import dev.beomseok.jibbap.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealApiController {
    private final MealService mealService;

    @PostMapping("groups/{uuid}/users/{kakaoId}")
    public ResponseEntity createMealInfo(
            @PathVariable String uuid,
            @PathVariable String kakaoId,
            @RequestBody MealRequest mealRequest
    ) {
        if (uuid == null || kakaoId == null || mealRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Boolean isCreated = mealService.createMealInfo(uuid, kakaoId, mealRequest);
        HttpStatus httpStatus = isCreated?HttpStatus.CREATED:HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }

    @PutMapping("groups/{uuid}/users/{kakaoId}")
    public ResponseEntity updateMealInfo(
            @PathVariable String uuid,
            @PathVariable String kakaoId,
            @RequestBody MealRequest mealRequest
    ) {
        if (uuid == null || kakaoId == null || mealRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Boolean isUpdated = mealService.updateMealInfo(uuid, kakaoId, mealRequest);
        HttpStatus httpStatus = isUpdated?HttpStatus.OK:HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).build();
    }
}
