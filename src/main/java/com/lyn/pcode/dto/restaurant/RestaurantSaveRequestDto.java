package com.lyn.pcode.dto.restaurant;

import com.lyn.pcode.dto.validation.DeliveryFeeUnit;
import com.lyn.pcode.dto.validation.OrderPriceUnit;
import com.lyn.pcode.models.restaurant.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
public class RestaurantSaveRequestDto {

    @NotBlank(message = "음식점 이름은 필수 항목입니다.")
    private final String name;

    @NotNull(message = "최소 주문 가격은 필수 항목입니다.")
    @Range(min = 1000, max = 100000,
           message = "최소 주문 가격은 천원 이상 십만원 이하의 값이여야 합니다.")
    @OrderPriceUnit(message = "최소 주문 가격은 100원 단위로만 입력가능합니다.")
    private final Integer minOrderPrice;

    @NotNull(message = "배달 요금은 필수 항목입니다.")
    @Range(min = 0, max = 10000,
           message = "배달 요금은 0원 이상 만원 이하의 값이여야 합니다.")
    @DeliveryFeeUnit(message = "배달 요금은 500원 단위로만 입력가능합니다.")
    private final Integer deliveryFee;

    @Builder
    public RestaurantSaveRequestDto(String name, Integer minOrderPrice, Integer deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();
    }
}
