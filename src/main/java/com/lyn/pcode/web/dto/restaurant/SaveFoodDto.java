package com.lyn.pcode.web.dto.restaurant;

import com.lyn.pcode.web.dto.restaurant.validation.OrderPriceUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SaveFoodDto {

    @NotNull(message = "name:음식명은 필수 입력 항목입니다")
    @NotBlank(message = "name:음식명은 필수 입력 항목입니다")
    private final String name;

    @NotNull(message = "price:음식 가격은 필수 입력 합목입니다")
    @Range(min = 100, max = 1000000, message = "가격은 100 ~ 1,000,000 범위의 값만 유효합니다")
    @OrderPriceUnit(message = "price:가격은 100원 단위로만 입력가능합니다")
    private final Integer price;
}
