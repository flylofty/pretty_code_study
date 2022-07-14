package com.lyn.pcode.web.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter // 클래스명 변경 FoodOrderRequestDto -> OrderFoodRequestDto
@NoArgsConstructor
public class OrderFoodRequestDto {
    private Long restaurantId;
    private List<@Valid OrderFoodInfoDto> foods;

    @Builder
    public OrderFoodRequestDto(Long restaurantId, List<OrderFoodInfoDto> foods) {
        this.restaurantId = restaurantId;
        this.foods = foods;
    }
}
