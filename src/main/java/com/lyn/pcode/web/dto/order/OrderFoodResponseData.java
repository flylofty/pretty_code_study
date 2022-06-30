package com.lyn.pcode.web.dto.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.lyn.pcode.models.order.OrderFood;
import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderFoodResponseData {
    private String restaurantName;
    private List<OrderFoodSearchInfo> foods;
    private Integer deliveryFee;
    private Integer totalPrice = 0;

    public OrderFoodResponseData(Restaurant restaurant, List<OrderFood> entities)
    {
        this.restaurantName = restaurant.getName();
        this.deliveryFee = restaurant.getDeliveryFee();

        this.foods = entities.stream()
                .map(OrderFoodSearchInfo::new)
                .collect(Collectors.toList());

        for (OrderFoodSearchInfo searchInfo : this.foods) {
            this.totalPrice += searchInfo.getPrice();
        }

        this.totalPrice += this.deliveryFee;
    }
}
