package com.lyn.pcode.web.dto.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@RequiredArgsConstructor // 클래스명 변경 FoodOrderInfoDto -> OrderFoodInfoDto
public class OrderFoodInfoDto {

    private Long id;

    @Range(min = 1, max = 100, message = "수량은 1 ~ 100 범위의 숫자만 입력 가능합니다")
    private Integer quantity;
}
