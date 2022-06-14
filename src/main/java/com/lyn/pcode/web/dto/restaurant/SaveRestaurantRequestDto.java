package com.lyn.pcode.web.dto.restaurant;

import com.lyn.pcode.models.restaurant.Restaurant;
import com.lyn.pcode.web.dto.restaurant.validation.MoneyUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SaveRestaurantRequestDto {

    @NotBlank(message = "name:음식점 이름은 필수 항목입니다.")
    private final String name;

    @NotNull(message = "minOrderPrice:최소 주문 가격은 필수 항목입니다.")
    @Range(min = 1000, max = 100000,
           message = "minOrderPrice:최소 주문 가격은 천원 이상 십만원 이하의 값이여야 합니다.")
    @MoneyUnit(message = "minOrderPrice:최소 주문 가격은 100원 단위로만 입력가능합니다.")
    private final Integer minOrderPrice;

    @NotNull(message = "deliveryFee:배달 요금은 필수 항목입니다.")
    @Range(min = 0, max = 10000,
           message = "deliveryFee:배달 요금은 0원 이상 만원 이하의 값이여야 합니다.")
    @MoneyUnit(message = "deliveryFee:배달 요금은 500원 단위로만 입력가능합니다.")
    private final Integer deliveryFee;

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();
    }
}
