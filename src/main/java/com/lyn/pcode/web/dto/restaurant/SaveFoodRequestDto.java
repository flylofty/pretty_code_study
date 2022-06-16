package com.lyn.pcode.web.dto.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SaveFoodRequestDto {
    @Valid
    private final List<SaveFoodDto> foods;
}
