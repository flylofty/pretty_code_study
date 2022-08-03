package com.lyn.pcode.web.restaurant;

import com.lyn.pcode.dto.restaurant.RestaurantSaveRequestDto;
import com.lyn.pcode.dto.restaurant.RestaurantSaveResponseDto;
import com.lyn.pcode.dto.restaurant.RestaurantsResponseDto;
import com.lyn.pcode.service.FoodService;
import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.dto.food.FoodsResponseDto;
import com.lyn.pcode.dto.food.SaveFoodRequestDto;
import com.lyn.pcode.dto.food.GeneralResponseDto;
import com.lyn.pcode.dto.validation.ValidationSequence;
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
    public ResponseEntity<RestaurantSaveResponseDto> saveRestaurant(@RequestBody @Valid RestaurantSaveRequestDto requestDto) {
        return ResponseEntity
                .ok()
                .body(restaurantService.saveRestaurant(requestDto));
    }

    @GetMapping("/api/v1/restaurants")
    public ResponseEntity<RestaurantsResponseDto> getRestaurants() {
        return ResponseEntity
                .ok()
                .body(new RestaurantsResponseDto(restaurantService.getRestaurants()));
    }

    @PostMapping("/api/v1/restaurants/{restaurantId}/foods")
    public ResponseEntity<GeneralResponseDto> saveFoods(@RequestBody @Validated(ValidationSequence.class) SaveFoodRequestDto requestDto,
                                                        @PathVariable Long restaurantId) throws Exception {
        foodService.saveFoods(requestDto, restaurantId);
        return ResponseEntity
                .ok()
                .body(new GeneralResponseDto());
    }

    @GetMapping("/api/v1/restaurants/{restaurantId}/foods")
    public ResponseEntity<FoodsResponseDto> getFoods(@PathVariable Long restaurantId) throws Exception {
        return ResponseEntity
                .ok()
                .body(new FoodsResponseDto(foodService.getFoods(restaurantId)));
    }
}
