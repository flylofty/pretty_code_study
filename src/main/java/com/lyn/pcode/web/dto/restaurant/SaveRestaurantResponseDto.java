package com.lyn.pcode.web.dto.restaurant;

import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SaveRestaurantResponseDto {

    private boolean success;
    private String message;
    private List<Object> data = new ArrayList<>();

    public SaveRestaurantResponseDto(BindingResult bindingResult) {
        this.success = false;
        this.message = "Validation Failed";

        // 중복 코드, 우아하지 못하다!
        bindingResult.getAllErrors()
                .forEach(objectError -> {
                    String[] errorArray = objectError.getDefaultMessage().split(":");
                    if (errorArray[0].equals("name")) {
                        Map<String, String> name = new HashMap<>();
                        name.put("name", errorArray[1]);
                        data.add(name);
                    } else if (errorArray[0].equals("minOrderPrice")) {
                        Map<String, String> minOrderPrice = new HashMap<>();
                        minOrderPrice.put("minOrderPrice", errorArray[1]);
                        data.add(minOrderPrice);
                    } else {
                        Map<String, String> deliveryFee = new HashMap<>();
                        deliveryFee.put("deliveryFee", errorArray[1]);
                        data.add(deliveryFee);
                    }
                });
    }

    // 임시 생성자
    public SaveRestaurantResponseDto() {
        this.success = true;
        this.message = "Request Successful";
    }
}
