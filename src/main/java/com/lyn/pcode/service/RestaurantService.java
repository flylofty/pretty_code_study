package com.lyn.pcode.service;

import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.restaurant.RestaurantDto;
import com.lyn.pcode.web.dto.restaurant.RestaurantSaveRequestDto;
import com.lyn.pcode.web.dto.restaurant.RestaurantSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantSaveResponseDto saveRestaurant(RestaurantSaveRequestDto requestDto) {
        return new RestaurantSaveResponseDto(restaurantRepository.save(requestDto.toEntity()));
    }

    public List<RestaurantDto> getRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantDto::new)
                .collect(Collectors.toList());
    }
}
