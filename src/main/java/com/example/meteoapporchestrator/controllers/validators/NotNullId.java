package com.example.meteoapporchestrator.controllers.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotNullIdValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullId {
    String message() default "The Id field must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
