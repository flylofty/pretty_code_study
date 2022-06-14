package com.lyn.pcode.service;

import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantRequestDto;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public SaveRestaurantResponseDto save(SaveRestaurantRequestDto requestDto) {
        return new SaveRestaurantResponseDto(restaurantRepository.save(requestDto.toEntity()));
    }
}