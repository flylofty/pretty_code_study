package com.lyn.pcode.web.dto.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodOrderResponseDto {
    private String code;
    private String message;
    private OrderDataDto data;

    public FoodOrderResponseDto(OrderDataDto data) {
        this.code = "200";
        this.message = "요청 성공";
        this.data = data;
    }
}
