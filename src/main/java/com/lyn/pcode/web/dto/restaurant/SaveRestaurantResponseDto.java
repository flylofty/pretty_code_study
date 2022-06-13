package com.lyn.pcode.web.dto.restaurant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class SaveRestaurantResponseDto {

    private String code;
    private String message;
    private Map<String, String> data;

    public SaveRestaurantResponseDto(BindingResult bindingResult) {
        this.code = "400";
        this.message = "사용자 입력 오류";

        // DTO 안에 이러한 로직인 있는게 아닌 것 같음
        makeDetails(bindingResult);
    }

    private void makeDetails(BindingResult bindingResult) {
        data = new HashMap<>();
        bindingResult.getAllErrors()
                .forEach(objectError -> {
                    String[] errorArray = objectError.getDefaultMessage().split(":");
                    if (isNeededFieldName(errorArray[0])) {
                        data.put(errorArray[0], errorArray[1]);
                    }
                });
    }

    private boolean isNeededFieldName(String fieldName) {
        // DTO 안에 이러한 로직인 있는게 아닌 것 같음
        return fieldName.equals("name") || fieldName.equals("minOrderPrice") || fieldName.equals("deliveryFee");
    }

    public SaveRestaurantResponseDto() {
        this.code = "200";
        this.message = "성공";
    }
}
