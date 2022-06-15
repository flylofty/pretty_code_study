package com.lyn.pcode.web.dto.restaurant;

import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantDto {

    private String id;
    private String name;
    private String minOrderPrice;
    private String deliveryFee;

    public RestaurantDto(Restaurant entity) {
        this.id = String.valueOf(entity.getId());
        this.name = entity.getName();
        this.minOrderPrice = String.valueOf(entity.getMinOrderPrice());
        this.deliveryFee = String.valueOf(entity.getDeliveryFee());
    }
}
