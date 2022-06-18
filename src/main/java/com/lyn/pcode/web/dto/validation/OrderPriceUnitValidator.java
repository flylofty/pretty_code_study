package com.lyn.pcode.web.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderPriceUnitValidator implements ConstraintValidator<OrderPriceUnit, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 100 == 0;
    }
}
