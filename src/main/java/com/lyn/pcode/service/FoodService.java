package com.lyn.pcode.service;

import com.lyn.pcode.exception.FoodNameExistException;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.web.dto.food.SaveFoodDto;
import com.lyn.pcode.web.dto.food.SaveFoodRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    public void saveFoods(SaveFoodRequestDto requestDto, Long restaurantId) throws Exception {

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

        foodRepository.saveAll(requestDto.toEntities(restaurantId));
    }

    private boolean isFoodNameExist(Long restaurantId, String foodName) {
        return foodRepository.existsByRestaurantIdAndName(restaurantId, foodName);
    }
}
