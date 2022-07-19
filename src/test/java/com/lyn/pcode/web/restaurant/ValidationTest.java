package com.lyn.pcode.web.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyn.pcode.web.dto.restaurant.RestaurantSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("음식점 등록 이름 누락되었을 경우, BAD_REQUEST(400)를 전달한다")
    void testMissingRestaurantName() throws Exception {
        // given
        String json = getJsonRequest("", 4900, 2000);

        //expected
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.name").value("음식점 이름은 필수 항목입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("음식점 등록 최소 주문 가격 범위 밖의 값일 경우, BAD_REQUEST(400)를 전달한다")
    void testMissingRestaurantMinOrderPrice1() throws Exception {
        // given
        final int WRONG_RANGE = 1000000;
        String json = getJsonRequest("쉐이크쉑 청담점", WRONG_RANGE, 2000);

        //expected
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.minOrderPrice").value("최소 주문 가격은 천원 이상 십만원 이하의 값이여야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("음식점 등록 최소 주문 가격 100원 단위 아닐 경우, BAD_REQUEST(400)를 전달한다")
    void testMissingRestaurantMinOrderPrice2() throws Exception {
        // given
        final int WRONG_PRICE_UNIT = 2220;
        String json = getJsonRequest("쉐이크쉑 청담점", WRONG_PRICE_UNIT, 3500);

        //expected
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.minOrderPrice").value("최소 주문 가격은 100원 단위로만 입력가능합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("음식점 등록 배달비 허용값 아닐 경우, BAD_REQUEST(400)를 전달한다")
    void testMissingRestaurantDeliveryFee1() throws Exception {
        // given
        final int WRONG_DELIVERY_FEE_RANGE = 11000;
        String json = getJsonRequest("쉐이크쉑 청담점", 2500, WRONG_DELIVERY_FEE_RANGE);

        //expected
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.deliveryFee").value("배달 요금은 0원 이상 만원 이하의 값이여야 합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("음식점 등록 배달비 500원 단위 아닐 경우, BAD_REQUEST(400)를 전달한다")
    void testMissingRestaurantDeliveryFee2() throws Exception {
        // given
        final int WRONG_DELIVERY_FEE_UNIT = 5550;
        String json = getJsonRequest("쉐이크쉑 청담점", 2500, WRONG_DELIVERY_FEE_UNIT);

        //expected
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.deliveryFee").value("배달 요금은 500원 단위로만 입력가능합니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    private String getJsonRequest(String name, int minOrderPrice, int deliveryFee) throws JsonProcessingException {
        RestaurantSaveRequestDto request = RestaurantSaveRequestDto.builder()
                .name(name)
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();

        return objectMapper.writeValueAsString(request);
    }
}
