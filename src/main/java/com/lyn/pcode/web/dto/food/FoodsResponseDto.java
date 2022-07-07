package com.lyn.pcode.web.dto.food;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FoodsResponseDto {
    private String code;
    private String message;
    private List<FoodsDto> data;

    public FoodsResponseDto(List<FoodsDto> data) {
        this.code = "200";
        this.message = "요청 성공";
        this.data = data;
    }
}
