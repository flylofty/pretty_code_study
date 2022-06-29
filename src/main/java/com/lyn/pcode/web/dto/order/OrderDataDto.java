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

    public OrderDataDto(Restaurant restaurant,
                        List<FoodOrderSearchResultDto> searchResultList,
                        Integer totalPrice)
    {
        this.restaurantName = restaurant.getName();
        this.foods = searchResultList;
        this.deliveryFee = restaurant.getDeliveryFee();
        this.totalPrice = totalPrice + this.deliveryFee;
    }
}
