package com.lyn.pcode.web.dto.validation;

import javax.validation.GroupSequence;

@GroupSequence({ValidationGroups.NotNullGroup.class, ValidationGroups.NotBlankGroup.class, ValidationGroups.RangeGroup.class, ValidationGroups.UnitGroup.class})
public interface ValidationSequence {
}
