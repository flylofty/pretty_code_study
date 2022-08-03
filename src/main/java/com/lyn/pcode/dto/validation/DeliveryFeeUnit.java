package com.lyn.pcode.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD}) // 해당 어노테이션을 필드에만 선언 가능
@Retention(RUNTIME) // 해당 어노테이션이 유지되는 시간으로써 런타임까지 유효함
@Constraint(validatedBy = DeliveryFeeUnitValidator.class) // DeliveryFeeValidator 를 통해 유효성 검사를 진행
@Documented // JavaDoc 생성시 Annotation 에 대한 정보도 함께 생성
public @interface DeliveryFeeUnit {

    // 유효하지 않을 경우 반환할 메시지
    String message() default "올바른 단위의 금액으로 입력해주세요.";

    // 유효성 검증이 진행될 그룹
    Class<?>[] groups() default {};

    // 유효성 검증 시에 전달할 메타 정보
    Class<? extends Payload>[] payload() default {};
}
