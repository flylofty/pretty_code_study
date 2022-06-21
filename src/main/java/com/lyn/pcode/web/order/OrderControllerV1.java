package com.lyn.pcode.web.order;

import com.lyn.pcode.web.dto.order.SaveOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class OrderControllerV1 {

    @PostMapping("/api/v1/orders")
    public void saveOrder(@RequestBody @Valid SaveOrderDto requestDto) {
    }
}
