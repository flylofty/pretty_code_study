package com.lyn.pcode.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.order.Order;
import com.lyn.pcode.models.order.OrderFood;
import com.lyn.pcode.models.order.OrderRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.order.OrderFoodInfoDto;
import com.lyn.pcode.web.dto.order.OrderFoodRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerV1Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void clear() {
        restaurantRepository.deleteAll();
        foodRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("주문_음식_요청_테스트")
    void saveOrder() throws Exception {

        // given
        Restaurant savedRestaurant = restaurantRepository.save(new Restaurant("쉐이크쉑 청담점", 5000, 2000));
        foodRepository.save(new Food(savedRestaurant, "쉐이크쉑 버거", "10900"));
        foodRepository.save(new Food(savedRestaurant, "치즈 감자튀김", "4900"));
        foodRepository.save(new Food(savedRestaurant, "쉐이크", "5900"));

        List<OrderFoodInfoDto> foods = new ArrayList<>();
        foods.add(new OrderFoodInfoDto(1L, 1));
        foods.add(new OrderFoodInfoDto(2L, 2));
        foods.add(new OrderFoodInfoDto(3L, 3));

        OrderFoodRequestDto request = OrderFoodRequestDto.builder()
                .restaurantId(1L)
                .foods(foods)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, orderRepository.count());
        Order order = orderRepository.findAll().get(0);

        List<OrderFood> orderFoodList = order.getOrderFoods();
        Integer totalPrice = 0;
        for (OrderFood orderFood : orderFoodList) {
            totalPrice += orderFood.getTotalFoodPrice();
        }
        assertEquals(totalPrice, 40400);
    }
}