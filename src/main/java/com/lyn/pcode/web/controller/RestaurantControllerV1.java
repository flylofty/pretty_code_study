package com.lyn.pcode.web.controller;

import com.lyn.pcode.web.dto.restaurant.SaveRestaurantRequestDto;
import com.lyn.pcode.web.dto.restaurant.SaveRestaurantResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class RestaurantControllerV1 {

    @PostMapping("/restaurants")
    public ResponseEntity<SaveRestaurantResponseDto> save(
            @RequestBody @Valid SaveRestaurantRequestDto requestDto,
                                BindingResult bindingResult
    ) {

        log.info("레스토랑 저장 요청 컨트롤러");

        if (bindingResult.hasErrors()) {

            /**
             * 해당 컨트롤러가 호출되지 않는 경우도 있음
             * 그래도 개선점을 찾고 적용하기 위해 우선 구현!
             * 더 나아지게 할 수 있는 방법은 'API 예외처리'!!
             */

            // 인증 및 인가가 시 headers 부분도 필요하지 않을까 싶다.
            return ResponseEntity
                    .badRequest()
                    .body(new SaveRestaurantResponseDto(bindingResult));
        }

        // 레스토랑 서비스 호출

        return ResponseEntity.ok().body(new SaveRestaurantResponseDto());
    }
}
