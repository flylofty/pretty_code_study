package com.lyn.pcode.web.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.models.restaurant.RestaurantRepository;
import com.lyn.pcode.service.RestaurantService;
import com.lyn.pcode.web.dto.restaurant.RestaurantSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    RestaurantService restaurantService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @BeforeEach
    void clear() {
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/v1/restaurants 요청 시 식당 기본 정보가 DB에 저장된다.")
    void saveRestaurant() throws Exception {

        //given
        RestaurantSaveRequestDto clientRequest = new RestaurantSaveRequestDto("쉐이크쉑 청담점", 5000, 2000);
        ObjectMapper objectMapper = new ObjectMapper();
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
    @DisplayName("GET /api/v1/restaurants 음식점 하나 조회")
    void getRestaurantInfo() throws Exception {
        // given
        Restaurant restaurant = Restaurant.builder()
                .name("쉐이크쉑 청담점")
                .minOrderPrice(5000)
                .deliveryFee(2000)
                .build();

        restaurantRepository.save(restaurant);

        // expected
        mockMvc.perform(get("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("쉐이크쉑 청담점"))
                .andExpect(jsonPath("$.data[0].minOrderPrice").value(5000))
                .andExpect(jsonPath("$.data[0].deliveryFee").value(2000))
                .andDo(print());
    }
}
















