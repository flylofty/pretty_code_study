package com.lyn.pcode.web.restaurant.advice;

import com.lyn.pcode.web.restaurant.RestaurantControllerV1;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = RestaurantControllerV1.class)
public class RestaurantControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SaveRestaurantResponseDto MethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new SaveRestaurantResponseDto(makeData(e.getBindingResult()));
    }

    private Map<String, String> makeData(BindingResult bindingResult) {

        Map<String, String>  data = new HashMap<>();

        bindingResult.getAllErrors()
                .forEach(objectError -> {
                    String[] errorArray = objectError.getDefaultMessage().split(":");
                    if (isNeededFieldName(errorArray[0])) {
                        data.put(errorArray[0], errorArray[1]);
                    }
                });

        return data;
    }

    private boolean isNeededFieldName(String fieldName) {
        return fieldName.equals("name") || fieldName.equals("minOrderPrice") || fieldName.equals("deliveryFee");
    }
}
