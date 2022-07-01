package com.lyn.pcode.web.dto.order;

import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@Getter // 클래스명 변경 FoodOrderRequestDto -> OrderFoodRequestDto
public class OrderFoodRequestDto {
    private Long restaurantId;
    private List<@Valid OrderFoodInfoDto> foods;

    public List<OrderFoodInfoDto> getOrderFoodInfoList() {
        return foods;
    }
}
