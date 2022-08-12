package com.lyn.pcode.service;

import com.lyn.pcode.exception.GlobalExistException;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.dto.food.FoodsDto;
import com.lyn.pcode.dto.food.SaveFoodRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public void saveFoods(SaveFoodRequestDto requestDto, Long restaurantId) {
        Restaurant findRestaurant = getRestaurant(restaurantId);
        foodRepository.saveAll(requestDto.toEntities(findRestaurant));
    }

    public List<FoodsDto> getFoods(Long restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        return restaurant.getMenu().stream()
                .map(FoodsDto::new)
                .collect(Collectors.toList());
    }

    private Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new GlobalExistException("존재하지 않는 음식점입니다")
        );
    }
}
