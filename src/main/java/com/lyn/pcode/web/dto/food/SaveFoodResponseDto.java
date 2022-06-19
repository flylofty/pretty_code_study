package com.lyn.pcode.web.dto.food;

import com.lyn.pcode.exception.FoodNameExistException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SaveFoodResponseDto {

    private String code;
    private String message;
    private Map<String, String> data;

    public SaveFoodResponseDto(FoodNameExistException e) {
        this.code = "400";
        this.message = "사용자 입력 오류";
        data = new HashMap<>();
        String[] errorMessage = e.getMessage().split(":");
        data.put(errorMessage[0], errorMessage[1]);
    }

    public SaveFoodResponseDto() {
        this.code = "200";
        this.message = "요청 성공";
    }
}
