package com.lyn.pcode.web.dto.order;

import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.order.OrderFood;
import lombok.Getter;

@Getter // 클래스명 변경 FoodOrderSearchResultDto -> OrderFoodSearchResultDto -> OrderFoodSearchInfo
public class OrderFoodSearchInfo {

    private String name;
    private Integer quantity;
    private Integer price;

    public OrderFoodSearchInfo(OrderFood entity) {
        this.name = entity.getFoodName();
        this.quantity = entity.getQuantity();
        this.price = entity.getTotalFoodPrice();
    }
}
