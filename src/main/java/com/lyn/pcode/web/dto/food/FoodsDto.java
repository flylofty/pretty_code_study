package com.lyn.pcode.web.dto.food;

import com.lyn.pcode.models.food.Food;
import lombok.Getter;

@Getter
public class FoodsDto {
    private Long id;
    private String name;
    private String price;

    public FoodsDto(Food entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
    }
}
