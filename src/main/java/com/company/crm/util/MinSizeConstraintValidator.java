package com.company.crm.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinSizeConstraintValidator implements ConstraintValidator<MinSizeConstraint, List<? extends MaxSizeInterface>> {

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(List<? extends MaxSizeInterface> value, ConstraintValidatorContext context) {
        return !value.isEmpty();
    }
}