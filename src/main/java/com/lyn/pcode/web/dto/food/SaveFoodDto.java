package com.lyn.pcode.web.dto.food;

import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.web.dto.validation.OrderPriceUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.lyn.pcode.web.dto.validation.ValidationGroups.*;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SaveFoodDto {

    @NotNull(message = "name:음식명은 필수 입력 항목입니다", groups = NotNullGroup.class)
    @NotBlank(message = "name:음식명은 필수 입력 항목입니다", groups = NotBlankGroup.class)
    private final String name;

    @NotNull(message = "price:음식 가격은 필수 입력 합목입니다", groups = RangeGroup.class)
    @Range(min = 100, max = 1000000, message = "가격은 100 ~ 1,000,000 범위의 값만 유효합니다", groups = RangeGroup.class)
    @OrderPriceUnit(message = "price:가격은 100원 단위로만 입력가능합니다", groups = UnitGroup.class)
    private final Integer price;

    public Food toEntity(Restaurant restaurant) {
        return Food.builder()
                .name(name)
                .restaurant(restaurant)
                .price(String.valueOf(price))
                .build();
    }
}
