package com.core.hero.controller.validation;

import com.core.hero.enums.Power;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PowerValidatorLogic implements ConstraintValidator<PowerValidator, String> {
    @Override
    public boolean isValid(String power, ConstraintValidatorContext context) {
        return Power.existsName(power);
    }
}