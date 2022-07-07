package com.lyn.pcode.service;

import com.lyn.pcode.exception.FoodNameExistException;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.food.FoodsDto;
import com.lyn.pcode.web.dto.food.SaveFoodDto;
import com.lyn.pcode.web.dto.food.SaveFoodRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public void saveFoods(SaveFoodRequestDto requestDto, Long restaurantId) throws Exception {

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                // 임시
                () -> new IllegalArgumentException("restaurant:존재하지 않는 음식점입니다")
        );

        /**
         * 요청하는 이름마다 각각 DB에 존재 여부를 묻는 쿼리를 실행하는 것이 맞는지
         * 아니면 해당 restaurantId 식당의 음식 리스트를 가져와서 존재를 판단하는 것이 좋은지 모르겠음
         */

        List<SaveFoodDto> foodList = requestDto.getFoods();
        for (SaveFoodDto food : foodList) {
            if (isFoodNameExist(restaurantId, food.getName())) {
                throw new FoodNameExistException("name:'" + food.getName() + "'은/는 이미 존재하는 음식명입니다");
            }
        }

        foodRepository.saveAll(requestDto.toEntities(findRestaurant));
    }

    private boolean isFoodNameExist(Long restaurantId, String foodName) {
        return foodRepository.existsByRestaurantIdAndName(restaurantId, foodName);
    }

    public List<FoodsDto> getFoods(Long restaurantId) {

        Restaurant restaurant = getRestaurant(restaurantId);

        return restaurant.getMenu().stream()
                .map(FoodsDto::new)
                .collect(Collectors.toList());
    }

    private Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NoSuchElementException("해당 음식점이 존재하지 않습니다.")
        );
    }
}
