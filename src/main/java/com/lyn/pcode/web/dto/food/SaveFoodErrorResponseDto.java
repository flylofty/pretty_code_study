package com.lyn.pcode.web.dto.food;

import com.lyn.pcode.exception.FoodExistException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SaveFoodErrorResponseDto {

    private String code;
    private String message;
    private Map<String, String> data;

    public SaveFoodErrorResponseDto(FoodExistException e) {
        this.code = "400";
        this.message = "사용자 입력 오류";
        data = new HashMap<>();
        String[] errorMessage = e.getMessage().split(":");
        data.put(errorMessage[0], errorMessage[1]);
    }
}
