package com.lyn.pcode.web.controller;

import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantRequestDto;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RestaurantControllerV1 {

    private final RestaurantService restaurantService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SaveRestaurantResponseDto MethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new SaveRestaurantResponseDto(e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public SaveRestaurantResponseDto IllegalArgument(IllegalArgumentException e) {
        return new SaveRestaurantResponseDto();
    }

    @PostMapping("/restaurants")
    public void save(@RequestBody @Valid SaveRestaurantRequestDto requestDto) throws Exception {
        restaurantService.save(requestDto);
    }
}
