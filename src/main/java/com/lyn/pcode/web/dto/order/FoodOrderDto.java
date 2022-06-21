package com.lyn.pcode.web.dto.order;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class FoodOrderDto {

    private Long id;

    @Range(min = 1, max = 100, message = "quantity:수량은 1 ~ 100 범위의 숫자만 입력 가능합니다")
    private Integer quantity;
}
