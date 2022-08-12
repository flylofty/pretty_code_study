package com.lyn.pcode.dto.food;

import com.lyn.pcode.exception.GlobalExistException;
import lombok.Getter;

@Getter
public class GeneralResponseDto {

    private String code;
    private String message;

    public GeneralResponseDto() {
        this.code = "200";
        this.message = "요청 성공";
    }

    public GeneralResponseDto(String message) {
        this.code = "400";
        this.message = message;
    }
}
