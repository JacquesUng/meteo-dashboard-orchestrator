package com.example.meteoapporchestrator.controllers.validators;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullIdValidator implements ConstraintValidator<NullId, CollectConfigurationDto> {
    @Override
    public void initialize(NullId constraintAnnotation) {}

    @Override
    public boolean isValid(CollectConfigurationDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // La validation @NotNull est gérée séparément
        }
        return value.Id() == null;
    }
}
