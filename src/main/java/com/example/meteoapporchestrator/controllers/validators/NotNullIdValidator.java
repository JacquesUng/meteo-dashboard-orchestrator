package com.example.meteoapporchestrator.controllers.validators;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullIdValidator implements ConstraintValidator<NotNullId, CollectConfigurationDto> {
    @Override
    public void initialize(NotNullId constraintAnnotation) {}

    @Override
    public boolean isValid(CollectConfigurationDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // La validation @NotNull est gérée séparément
        }
        return value.Id() != null;
    }
}
