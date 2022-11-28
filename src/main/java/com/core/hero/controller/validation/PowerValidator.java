package com.core.hero.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PowerValidatorLogic.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PowerValidator {

    String message() default "It is not a valid power";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}