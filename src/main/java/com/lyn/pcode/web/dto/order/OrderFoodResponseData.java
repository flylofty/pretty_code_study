package com.lyn.pcode.web.dto.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.lyn.pcode.models.order.Order;
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
    private Integer totalPrice;

    public OrderFoodResponseData(Restaurant restaurant, List<OrderFood> entities) {
        this.restaurantName = restaurant.getName();
        this.deliveryFee = restaurant.getDeliveryFee();
        this.foods = getFoodList(entities);
        this.totalPrice = calculateTotalPrice();
    }

    public OrderFoodResponseData(Order entity) {
        this(entity.getRestaurant(), entity.getOrderFoods());
    }

    private List<OrderFoodSearchInfo> getFoodList(List<OrderFood> entities) {
        return entities.stream()
                .map(OrderFoodSearchInfo::new)
                .collect(Collectors.toList());
    }

    private Integer calculateTotalPrice() {
        int total = 0;
        for (OrderFoodSearchInfo searchInfo : this.foods) {
            total += searchInfo.getPrice();
        }

        total += this.deliveryFee;
        return total;
    }
}
