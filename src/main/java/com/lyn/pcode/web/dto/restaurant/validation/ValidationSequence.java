package com.lyn.pcode.web.dto.restaurant.validation;

import javax.validation.GroupSequence;

import static com.lyn.pcode.web.dto.restaurant.validation.ValidationGroups.*;

@GroupSequence({NotNullGroup.class, NotBlankGroup.class, RangeGroup.class, UnitGroup.class})
public interface ValidationSequence {
}
