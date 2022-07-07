package com.lyn.pcode.web.dto.order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrdersResponseDto {

    private String code;
    private String message;
    private List<OrderFoodResponseData> data;

    public OrdersResponseDto(List<OrderFoodResponseData> data) {
        this.code = "200";
        this.message = "요청 성공";
        this.data = data;
    }
}
