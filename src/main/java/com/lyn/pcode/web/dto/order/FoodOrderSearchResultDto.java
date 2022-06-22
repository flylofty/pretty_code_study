package com.lyn.pcode.web.dto.order;

import com.lyn.pcode.models.food.Food;
import lombok.Getter;

@Getter
public class FoodOrderSearchResultDto {

    private String name;
    private Integer quantity;
    private Integer price;
    public FoodOrderSearchResultDto(Food findFood, FoodOrderInfoDto foodDto) {
        this.name = findFood.getName();
        this.quantity = foodDto.getQuantity();
        this.price = quantity * Integer.parseInt(findFood.getPrice());
    }
}
