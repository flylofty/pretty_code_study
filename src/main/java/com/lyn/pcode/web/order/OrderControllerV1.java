package com.lyn.pcode.web.order;

import com.lyn.pcode.service.OrderService;
import com.lyn.pcode.web.dto.order.OrderFoodRequestDto;
import com.lyn.pcode.web.dto.order.OrderFoodResponseDto;
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

    @PostMapping("/api/v1/orders") // 메서드명 변경 foodOrder -> saveOrderFood
    public ResponseEntity<OrderFoodResponseDto> saveOrder(
            @RequestBody @Valid OrderFoodRequestDto requestDto) throws Exception
    {
        return ResponseEntity
                .ok()
                .body(new OrderFoodResponseDto(orderService.saveOrder(requestDto)));
    }
}
