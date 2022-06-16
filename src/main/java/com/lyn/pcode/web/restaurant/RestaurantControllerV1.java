package com.lyn.pcode.web.restaurant;

import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.web.dto.restaurant.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RestaurantControllerV1 {

    private final RestaurantService restaurantService;

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
    public void saveFood(@RequestBody @Valid SaveFoodRequestDto requestDto) {
        List<SaveFoodDto> foods = requestDto.getFoods();
        for (SaveFoodDto food : foods) {
            System.out.println(food.getName());
            System.out.println(food.getPrice());
            System.out.println("======================");
        }
    }
}
