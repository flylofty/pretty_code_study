package com.lyn.pcode.service;

import com.lyn.pcode.exception.GlobalExistException;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.order.Order;
import com.lyn.pcode.models.order.OrderFood;
import com.lyn.pcode.models.order.OrderFoodRepository;
import com.lyn.pcode.models.order.OrderRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.order.OrderFoodInfoDto;
import com.lyn.pcode.web.dto.order.OrderFoodRequestDto;
import com.lyn.pcode.web.dto.order.OrderFoodResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;

    @Transactional // 메서드명 변경 foodOrder -> saveOrderFood
    public OrderFoodResponseData saveOrder(OrderFoodRequestDto requestDto) throws Exception {
        // 음식점 조회
        Restaurant restaurant = getRestaurant(requestDto.getRestaurantId());
        // 주문 저장
        Order savedOrder = saveOrder(restaurant);
        // 주문_음식 저장
        List<OrderFood> savedOrderFoodList = saveOrderFood(requestDto, savedOrder);
        // 응답 데이터 리턴
        return new OrderFoodResponseData(restaurant, savedOrderFoodList);
    }

    public List<OrderFoodResponseData> getOrders() {
        return orderRepository.findAll().stream()
                .map(OrderFoodResponseData::new)
                .collect(Collectors.toList());
    }

    private List<OrderFood> saveOrderFood(OrderFoodRequestDto requestDto, Order savedOrder) throws Exception {
        // '주문_음식' 정보를 저장할 리스트
        List<OrderFood> orderFoodList = new ArrayList<>();

        // 사용자 요청 정보를 dto로부터 가져옴
        List<OrderFoodInfoDto> orderFoodInfoList = requestDto.getOrderFoodInfoList();
        for (OrderFoodInfoDto orderFoodInfo : orderFoodInfoList) {
            // 특정 음식 정보 조회
            Food food = getFood(orderFoodInfo);
            // '주문_음식' 저장 정보 리스트 축적
            orderFoodList.add(new OrderFood(savedOrder, food, orderFoodInfo));
        }

        // '주문_음식' 정보 저장
        return orderFoodRepository.saveAll(orderFoodList);
    }

    private Order saveOrder(Restaurant restaurant) {
        return orderRepository.save(new Order(restaurant));
    }

    private Restaurant getRestaurant(Long restaurantId) throws Exception {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new GlobalExistException("존재하지 않는 음식점입니다")
        );
    }

    private Food getFood(OrderFoodInfoDto foodOrderInfo) throws Exception {
        return foodRepository.findById(foodOrderInfo.getId()).orElseThrow(
                () -> new GlobalExistException("존재하지 않는 음식입니다")
        );
    }
}
