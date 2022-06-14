package com.lyn.pcode.web.dto.restaurant;

import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor
public class SaveRestaurantResponseDto {

    private String code;
    private String message;
    private Map<String, String> data;

    public SaveRestaurantResponseDto(Map<String, String> data) {
        this.code = "400";
        this.message = "사용자 입력 오류";
        this.data = data;
    }

    public SaveRestaurantResponseDto(Restaurant entity) {
        this.code = "200";
        this.message = "성공";
        data = new HashMap<>();
        data.put("id", entity.getId().toString());
        data.put("name", entity.getName());
        data.put("minOrderPrice", entity.getMinOrderPrice().toString());
        data.put("deliveryFee", entity.getDeliveryFee().toString());
    }
}
