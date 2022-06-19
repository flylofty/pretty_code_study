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

        List<SaveFoodDto> foodList = requestDto.getFoods();
        for (SaveFoodDto food : foodList) {
            if (isFoodNameExist(food.getName())) {
                throw new FoodNameExistException("name:'" + food.getName() + "'은/는 이미 존재하는 음식명입니다");
            }
        }

        foodRepository.saveAll(requestDto.toEntities(restaurantId));
    }

    private boolean isFoodNameExist(String foodName) {
        return foodRepository.existsByName(foodName);
    }
}
