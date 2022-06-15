package com.lyn.pcode.web.dto.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RestaurantsResponseDto {

    private String code;
    private String message;
    private List<RestaurantDto> data;

    public RestaurantsResponseDto(String code, String message, List<RestaurantDto> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
