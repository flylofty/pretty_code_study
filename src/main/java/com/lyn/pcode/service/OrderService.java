package com.lyn.pcode.service;

import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.order.Order;
import com.lyn.pcode.models.order.OrderRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.order.FoodOrderInfoDto;
import com.lyn.pcode.web.dto.order.FoodOrderRequestDto;
import com.lyn.pcode.web.dto.order.FoodOrderSearchResultDto;
import com.lyn.pcode.web.dto.order.OrderDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDataDto foodOrder(FoodOrderRequestDto requestDto) {

        // restaurantId를 통해 해당 식당의 정보를 찾아옴
        // 음식점 이름, 배달비 정보가 필요
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(
                () -> new NullPointerException("해당 레스토랑이 존재하지 않습니다")
        );

        // 음식 각각의 이름과 가격 정보가 필요
        List<FoodOrderInfoDto> foods = requestDto.getFoods();
        List<Order> orderList = new ArrayList<>();
        List<FoodOrderSearchResultDto> searchResultList = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();
        for (FoodOrderInfoDto food : foods) {
            Food findFood = foodRepository.findById(food.getId()).orElseThrow(
                    () -> new NullPointerException("해당 음식이 존재하지 않습니다.")
            );
            orderList.add(new Order(uuid, findFood, food.getQuantity()));
            searchResultList.add(new FoodOrderSearchResultDto(findFood, food));
        }

        orderRepository.saveAll(orderList);

        return new OrderDataDto(restaurant,searchResultList);
    }
}
