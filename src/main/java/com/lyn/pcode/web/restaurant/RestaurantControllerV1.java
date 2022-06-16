package com.lyn.pcode.web.restaurant;

import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.web.dto.restaurant.*;
import com.lyn.pcode.web.dto.restaurant.validation.ValidationSequence;
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
    public void saveFood(@RequestBody @Validated(ValidationSequence.class) SaveFoodRequestDto requestDto,
                         @PathVariable Long restaurantId)
    {
    }
}
