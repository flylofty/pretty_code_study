package com.lyn.pcode.web.order;

import com.lyn.pcode.service.OrderService;
import com.lyn.pcode.dto.order.OrderFoodRequestDto;
import com.lyn.pcode.dto.order.OrderFoodResponseDto;
import com.lyn.pcode.dto.order.OrdersResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderControllerV1 {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders") // 메서드명 변경 foodOrder -> saveOrderFood
    public ResponseEntity<OrderFoodResponseDto> saveOrder(
            @RequestBody @Valid OrderFoodRequestDto requestDto) throws Exception
    {
        return ResponseEntity
                .ok()
                .body(new OrderFoodResponseDto(orderService.saveOrder(requestDto)));
    }

    @GetMapping("/api/v1/orders")
    public ResponseEntity<OrdersResponseDto> getOrders() {
        return ResponseEntity
                .ok()
                .body(new OrdersResponseDto(orderService.getOrders()));
    }
}
