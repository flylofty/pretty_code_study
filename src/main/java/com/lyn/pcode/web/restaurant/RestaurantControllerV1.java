package com.lyn.pcode.web.restaurant;

import com.lyn.pcode.service.FoodService;
import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.web.dto.food.SaveFoodRequestDto;
import com.lyn.pcode.web.dto.food.SaveFoodResponseDto;
import com.lyn.pcode.web.dto.restaurant.*;
import com.lyn.pcode.web.dto.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RestaurantControllerV1 {

    private final RestaurantService restaurantService;
    private final FoodService foodService;

    @PostMapping("/api/v1/restaurants")
    public ResponseEntity<SaveRestaurantResponseDto> save(@RequestBody @Valid SaveRestaurantRequestDto requestDto) {
        return ResponseEntity
                .ok()
                .body(restaurantService.save(requestDto));
    }

    @GetMapping("/api/v1/restaurants")
    public ResponseEntity<RestaurantsResponseDto> getRestaurants() {
        return ResponseEntity
                .ok()
                .body(new RestaurantsResponseDto("200", "요청 성공", restaurantService.getRestaurants()));
    }

    @PostMapping("/api/v1/restaurants/{restaurantId}/foods")
    public ResponseEntity<SaveFoodResponseDto> saveFoods(@RequestBody @Validated(ValidationSequence.class) SaveFoodRequestDto requestDto,
                                                         @PathVariable Long restaurantId) throws Exception
    {
        foodService.saveFoods(requestDto, restaurantId);

        return ResponseEntity
                .ok()
                .body(new SaveFoodResponseDto());
    }
}
