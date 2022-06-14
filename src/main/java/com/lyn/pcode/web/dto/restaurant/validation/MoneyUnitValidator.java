package com.lyn.pcode.web.dto.restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyUnitValidator implements ConstraintValidator<MoneyUnit, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        if (value % 500 == 0) {
            return true;
        }

        return value % 100 == 0;
    }
}
