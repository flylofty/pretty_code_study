package com.lyn.pcode.web.dto.restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeliveryFeeUnitValidator implements ConstraintValidator<DeliveryFeeUnit, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 500 == 0;
    }
}
