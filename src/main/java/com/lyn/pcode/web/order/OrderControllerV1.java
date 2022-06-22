package com.lyn.pcode.web.order;

import com.lyn.pcode.service.OrderService;
import com.lyn.pcode.web.dto.order.FoodOrderRequestDto;
import com.lyn.pcode.web.dto.order.FoodOrderResponseDto;
import com.lyn.pcode.web.dto.order.OrderDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderControllerV1 {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public ResponseEntity<FoodOrderResponseDto> foodOrder(@RequestBody @Valid FoodOrderRequestDto requestDto) {

        OrderDataDto orderDataDto = orderService.foodOrder(requestDto);

        return ResponseEntity
                .ok()
                .body(new FoodOrderResponseDto(orderDataDto));
    }
}
