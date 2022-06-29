package com.lyn.pcode.service;

import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.order.Order;
import com.lyn.pcode.models.order.OrderFood;
import com.lyn.pcode.models.order.OrderFoodRepository;
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

    private final OrderFoodRepository orderFoodRepository;

    @Transactional
    public OrderDataDto foodOrder(FoodOrderRequestDto requestDto) {

        // restaurantId를 통해 해당 식당의 정보를 찾아옴
        // 음식점 이름, 배달비 정보가 필요
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 음식점입니다")
        );

        // 주문 저장
        Order savedOrder = orderRepository.save(new Order(restaurant));

        // 주문_음식 저장, 리팩토링 어떻게 하실?
        List<FoodOrderInfoDto> foodOrderInfoList = requestDto.getFoods();
        List<OrderFood> orderFoodList = new ArrayList<>();
        Integer totalPrice = 0;
        List<FoodOrderSearchResultDto> foodResultList = new ArrayList<>();

        for (FoodOrderInfoDto foodOrderInfo : foodOrderInfoList) {
            Food food = foodRepository.findById(foodOrderInfo.getId()).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 음식입니다")
            );

            orderFoodList.add(new OrderFood(savedOrder, food, foodOrderInfo));
            foodResultList.add(new FoodOrderSearchResultDto(food, foodOrderInfo));
            totalPrice += foodResultList.get(foodResultList.size() - 1).getPrice();
        }

        orderFoodRepository.saveAll(orderFoodList);

        return new OrderDataDto(restaurant, foodResultList, totalPrice);
    }
}
