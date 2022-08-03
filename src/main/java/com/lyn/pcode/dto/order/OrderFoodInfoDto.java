package com.lyn.pcode.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class OrderFoodInfoDto {

    private Long id;

    @Range(min = 1, max = 100, message = "수량은 1 ~ 100 범위의 숫자만 입력 가능합니다")
    private Integer quantity;

    public OrderFoodInfoDto(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
