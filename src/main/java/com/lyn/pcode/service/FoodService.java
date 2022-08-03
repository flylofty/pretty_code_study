package com.lyn.pcode.service;

import com.lyn.pcode.exception.FoodAlreadyExistException;
import com.lyn.pcode.exception.GlobalExistException;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.dto.food.FoodsDto;
import com.lyn.pcode.dto.food.SaveFoodDto;
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
    public void saveFoods(SaveFoodRequestDto requestDto, Long restaurantId) throws Exception {

        Restaurant findRestaurant = getRestaurant(restaurantId);

        /**
         * 요청하는 이름마다 각각 DB에 존재 여부를 묻는 쿼리를 실행하는 것이 맞는지
         * 아니면 해당 restaurantId 식당의 음식 리스트를 가져와서 존재를 판단하는 것이 좋은지 모르겠음
         */
//        validateFoodExistence(requestDto, restaurantId);
        foodRepository.saveAll(requestDto.toEntities(findRestaurant));
    }

    public List<FoodsDto> getFoods(Long restaurantId) throws Exception {
        Restaurant restaurant = getRestaurant(restaurantId);
        return restaurant.getMenu().stream()
                .map(FoodsDto::new)
                .collect(Collectors.toList());
    }

    private void validateFoodExistence(SaveFoodRequestDto requestDto, Long restaurantId) {
        List<SaveFoodDto> foodList = requestDto.getFoods();
        for (SaveFoodDto food : foodList) {
            if (isFoodExist(restaurantId, food.getName())) {
                throw new FoodAlreadyExistException("name:'" + food.getName() + "'은/는 이미 존재하는 음식 이름입니다");
            }
        }
    }

    private boolean isFoodExist(Long restaurantId, String foodName) {
        return foodRepository.existsByRestaurantIdAndName(restaurantId, foodName);
    }

    private Restaurant getRestaurant(Long restaurantId) throws Exception {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new GlobalExistException("존재하지 않는 음식점입니다")
        );
    }
}
