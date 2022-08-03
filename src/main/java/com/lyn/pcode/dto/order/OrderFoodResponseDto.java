package com.lyn.pcode.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 클래스명 변경 FoodOrderResponseDto -> OrderFoodResponseDto
public class OrderFoodResponseDto {
    private String code;
    private String message;
    private OrderFoodResponseData data;

    public OrderFoodResponseDto(OrderFoodResponseData data) {
        this.code = "200";
        this.message = "요청 성공";
        this.data = data;
    }
}
