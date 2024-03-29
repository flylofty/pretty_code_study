package com.lyn.pcode.web.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.models.food.Food;
import com.lyn.pcode.models.food.FoodRepository;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.service.FoodService;
import com.lyn.pcode.dto.food.SaveFoodDto;
import com.lyn.pcode.dto.food.SaveFoodRequestDto;
import com.lyn.pcode.dto.restaurant.RestaurantSaveRequestDto;
import com.lyn.pcode.service.RestaurantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RestaurantControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    @DisplayName("식당 기본 정보 등록")
    void saveRestaurant() throws Exception {

        //given
        RestaurantSaveRequestDto clientRequest = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientRequest);

        //when
        mockMvc.perform(post("/api/v1/restaurants")
                                .contentType(APPLICATION_JSON)
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
    @DisplayName("음식점 하나 조회")
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
    @DisplayName("특정 음식점 메뉴 등록")
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

    @Test
    @DisplayName("특정 음식점 메뉴 조회")
    void getMenuTest() throws Exception {

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

        foodService.saveFoods(request, restaurantId);

        em.flush();
        em.clear();

        // expected
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/restaurants/{restaurantId}/foods", restaurantId))
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json"))
                .andExpect(jsonPath("$.data[0].name").value("쉑버거 더블"))
                .andExpect(jsonPath("$.data[0].price").value("10900"))
                .andExpect(jsonPath("$.data[1].name").value("치즈 감자튀김"))
                .andExpect(jsonPath("$.data[1].price").value("4900"))
                .andExpect(jsonPath("$.data[2].name").value("쉐이크"))
                .andExpect(jsonPath("$.data[2].price").value("5900"))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("음식점 이름 중복 불가")
    void duplicateDisallowedRestaurantNameTest() {
        RestaurantSaveRequestDto request = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        restaurantRepository.save(request.toEntity());
        assertThrows(RuntimeException.class, () -> {
            RestaurantSaveRequestDto request2 = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 4000, 2500);
            restaurantRepository.save(request2.toEntity());
        });
    }

    @Test
    @DisplayName("음식점 이름 중복 불가 응답")
    void duplicateDisallowedRestaurantNameResponseTest() throws Exception {
        //given
        RestaurantSaveRequestDto request = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        restaurantService.saveRestaurant(request);

        RestaurantSaveRequestDto clientRequest = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(clientRequest);

        //when
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("음식점 메뉴 중복 불가 응답")
    void duplicateDisallowedFoodNameResponseTest() throws Exception {
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
        foodService.saveFoods(request, restaurantId);
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/foods", restaurantId)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}








