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

    public RestaurantsResponseDto(List<RestaurantDto> data) {
        this.code = "200";
        this.message = "요청 성공";
        this.data = data;
    }
}
