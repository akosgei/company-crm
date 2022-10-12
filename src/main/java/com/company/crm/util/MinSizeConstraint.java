package com.company.crm.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MinSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSizeConstraint {
    String message() default "The input list cannot contain less than 1 item.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
