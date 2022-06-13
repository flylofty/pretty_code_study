package com.lyn.pcode.web.dto.restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinOrderPriceValidator implements ConstraintValidator<MinOrderPriceUnit, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 100 == 0;
    }
}
