package com.example.meteoapporchestrator.controllers.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullIdValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullId {
    String message() default "The Id field must be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
