package com.lyn.pcode.web.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.order.OrderRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.dto.order.OrderFoodInfoDto;
import com.lyn.pcode.dto.order.OrderFoodRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
    EntityManager em;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    @DisplayName("주문_음식_요청_테스트")
    void saveOrder() throws Exception {
        // given
        Restaurant savedRestaurant = restaurantRepository.save(new Restaurant("쉐이크쉑 청담점", 5000, 2000));
        foodRepository.save(new Food(savedRestaurant, "쉐이크쉑 버거", "10900"));
        foodRepository.save(new Food(savedRestaurant, "치즈 감자튀김", "4900"));
        foodRepository.save(new Food(savedRestaurant, "쉐이크", "5900"));

        List<OrderFoodInfoDto> foods = new ArrayList<>();

        List<Food> menu = foodRepository.findAll();
        int quantityCount = 1;
        for (Food food : menu) {
            foods.add(new OrderFoodInfoDto(food.getId(), quantityCount++));
        }

        OrderFoodRequestDto request = OrderFoodRequestDto.builder()
                .restaurantId(savedRestaurant.getId())
                .foods(foods)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.restaurantName").value("쉐이크쉑 청담점"))
                .andExpect(jsonPath("$.data.deliveryFee").value(2000))
                .andExpect(jsonPath("$.data.totalPrice").value(40400))
                .andExpect(jsonPath("$.data.foods[0].name").value("쉐이크쉑 버거"))
                .andExpect(jsonPath("$.data.foods[0].quantity").value("1"))
                .andExpect(jsonPath("$.data.foods[0].price").value(10900))
                .andExpect(jsonPath("$.data.foods[1].name").value("치즈 감자튀김"))
                .andExpect(jsonPath("$.data.foods[1].quantity").value("2"))
                .andExpect(jsonPath("$.data.foods[1].price").value(9800))
                .andExpect(jsonPath("$.data.foods[2].name").value("쉐이크"))
                .andExpect(jsonPath("$.data.foods[2].quantity").value("3"))
                .andExpect(jsonPath("$.data.foods[2].price").value(17700))
                .andDo(print());
    }
}