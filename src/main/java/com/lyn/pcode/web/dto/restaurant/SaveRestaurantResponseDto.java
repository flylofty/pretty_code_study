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
        requestFieldSet("400", "사용자 입력 오류");
        this.data = data;
    }

    public SaveRestaurantResponseDto(Restaurant entity) {
        requestFieldSet("200", "성공");
        data = new HashMap<>();
        data.put("id", entity.getId().toString());
        data.put("name", entity.getName());
        data.put("minOrderPrice", entity.getMinOrderPrice().toString());
        data.put("deliveryFee", entity.getDeliveryFee().toString());
    }

    private void requestFieldSet(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
