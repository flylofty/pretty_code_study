package com.lyn.pcode.web.dto.food;

import com.lyn.pcode.models.food.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SaveFoodRequestDto {
    private final List<@Valid SaveFoodDto> foods;

    public List<Food> toEntities(Long restaurantId) {
        List<Food> result = new ArrayList<>();

        for (SaveFoodDto food : foods) {
            result.add(food.toEntity(restaurantId));
        }

        return result;
    }
}
