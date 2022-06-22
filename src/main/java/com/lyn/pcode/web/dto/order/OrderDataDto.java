package com.lyn.pcode.web.dto.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.lyn.pcode.models.restaurant.Restaurant;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderDataDto {
    private String restaurantName;
    private List<FoodOrderSearchResultDto> foods;
    private Integer deliveryFee;
    private Integer totalPrice;

    public OrderDataDto(Restaurant restaurant, List<FoodOrderSearchResultDto> searchResultList) {
        this.restaurantName = restaurant.getName();
        foods = searchResultList;
        this.deliveryFee = restaurant.getDeliveryFee();
        totalPrice = calculateTotalPrice();
    }

    private Integer calculateTotalPrice() {
        int total = 0;
        for (FoodOrderSearchResultDto food : foods) {
            total += food.getPrice();
        }
        return total;
    }
}
