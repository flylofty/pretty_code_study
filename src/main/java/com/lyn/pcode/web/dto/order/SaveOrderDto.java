package com.lyn.pcode.web.dto.order;

import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@Getter
public class SaveOrderDto {
    private Long restaurantId;
    private List<@Valid FoodOrderDto> foods;
}
