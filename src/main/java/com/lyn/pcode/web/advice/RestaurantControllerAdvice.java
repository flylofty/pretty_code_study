package com.lyn.pcode.web.advice;

import com.lyn.pcode.exception.FoodAlreadyExistException;
import com.lyn.pcode.exception.GlobalExistException;
import com.lyn.pcode.dto.food.GeneralResponseDto;
import com.lyn.pcode.dto.food.SaveFoodErrorResponseDto;
import com.lyn.pcode.web.order.OrderControllerV1;
import com.lyn.pcode.web.restaurant.RestaurantControllerV1;
import com.lyn.pcode.dto.restaurant.RestaurantSaveResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(assignableTypes = {RestaurantControllerV1.class, OrderControllerV1.class})
public class RestaurantControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public GeneralResponseDto handleNameDuplicationException(DataIntegrityViolationException e) {
        return new GeneralResponseDto("이름 중복");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GlobalExistException.class)
    public GeneralResponseDto handleGlobalExistException(GlobalExistException e) {
        return new GeneralResponseDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FoodAlreadyExistException.class)
    public SaveFoodErrorResponseDto handleFoodExistenceException(FoodAlreadyExistException e) {
        return new SaveFoodErrorResponseDto(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestaurantSaveResponseDto handleMethodArgumentNotValidation(MethodArgumentNotValidException e) {
        return new RestaurantSaveResponseDto(getErrorData(e.getBindingResult()));
    }

    private Map<String, String> getErrorData(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> validation = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            validation.put(getCorrectFieldName(fieldError.getField()), fieldError.getDefaultMessage());
        }

        return validation;
    }

    private String getCorrectFieldName(String field) {
        if (field.contains(".")) {
            String[] strings = field.split("\\.");
            return strings[1];
        }
        return field;
    }
}
