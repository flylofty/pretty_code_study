package com.lyn.pcode.web.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.web.dto.food.SaveFoodDto;
import com.lyn.pcode.web.dto.food.SaveFoodRequestDto;
import com.lyn.pcode.web.dto.restaurant.RestaurantSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RestaurantControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void clear() {
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("POST 요청 시 식당 기본 정보가 DB에 저장된다.")
    void saveRestaurant() throws Exception {

        //given
        RestaurantSaveRequestDto clientRequest = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientRequest);

        //when
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
//                        .content("{\"name\":\"쉐이크쉑 청담점\", \"minOrderPrice\":\"5000\", \"deliveryFee\":\"2000\"}")
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, restaurantRepository.count());
        Restaurant restaurant = restaurantRepository.findAll().get(0);
        assertEquals("쉐이크쉑 청담점", restaurant.getName());
        assertEquals(5000, restaurant.getMinOrderPrice());
        assertEquals(2000, restaurant.getDeliveryFee());
    }

    @Test
    @DisplayName("GET 음식점 하나 조회")
    void getRestaurantInfo() throws Exception {
        // given
        Restaurant restaurant = Restaurant.builder()
                .name("쉐이크쉑 청담점")
                .minOrderPrice(5000)
                .deliveryFee(2000)
                .build();

        restaurant = restaurantRepository.save(restaurant);

        final Long restaurantId = restaurant.getId();

        // expected
        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(restaurantId))
                .andExpect(jsonPath("$.data[0].name").value("쉐이크쉑 청담점"))
                .andExpect(jsonPath("$.data[0].minOrderPrice").value(5000))
                .andExpect(jsonPath("$.data[0].deliveryFee").value(2000))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("POST 특정 음식점 메뉴 등록")
    void saveFoods() throws Exception {

        // given
        Restaurant restaurant = Restaurant.builder()
                .name("쉐이크쉑 청담점")
                .minOrderPrice(5000)
                .deliveryFee(2000)
                .build();

        restaurant = restaurantRepository.save(restaurant);

        final Long restaurantId = restaurant.getId();


        List<SaveFoodDto> foods = new ArrayList<>();
        foods.add(new SaveFoodDto("쉑버거 더블", 10900));
        foods.add(new SaveFoodDto("치즈 감자튀김", 4900));
        foods.add(new SaveFoodDto("쉐이크", 5900));
        SaveFoodRequestDto request = new SaveFoodRequestDto(foods);

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/foods", restaurantId)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        em.flush();
        em.clear();

        // then
        assertEquals(1L, restaurantRepository.count());
        Restaurant savedRestaurant = restaurantRepository.findAll().get(0);
        List<Food> menu = savedRestaurant.getMenu();

        int len = menu.size();
        assertEquals(len, foods.size());
        for (int i = 0; i < len; ++i) {
            Food food = menu.get(i);
            assertEquals(food.getName(), foods.get(i).getName());
            assertEquals(Integer.parseInt(food.getPrice()), foods.get(i).getPrice());
        }
    }
}
















