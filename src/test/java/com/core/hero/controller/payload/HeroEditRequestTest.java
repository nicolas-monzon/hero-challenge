package com.core.hero.controller.payload;

import com.core.hero.enums.Power;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class HeroEditRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private HeroEditRequest heroEditRequest;

    @BeforeEach
    public void createValidator() {
        heroEditRequest = this.validInstance();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterEach
    public void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Should create new instance")
    public void shouldCreateNewInstance() {
        assertDoesNotThrow(() -> new HeroEditRequest());
    }

    @Test
    @DisplayName("Should validate if a value is invalid.")
    public void shouldCheckIfTheAttributeIsInvalid() {
        Set<ConstraintViolation<HeroEditRequest>> violations = validator.validate(this.heroEditRequest);
        assertIsEmpty(violations);

        this.heroEditRequest.setId(0L);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setId(-1L);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setId(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        String invalidName = IntStream.range(0, 70).boxed().map(String::valueOf).collect(Collectors.joining());
        this.heroEditRequest.setName(invalidName);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setName(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        this.heroEditRequest.setStrength(-1);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setStrength(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        this.heroEditRequest.setSpeed(-2);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setSpeed(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        this.heroEditRequest.setDurability(-3);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setDurability(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        this.heroEditRequest.setPower("ASD");
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest.setPower(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

        this.heroEditRequest = this.validInstance();
        this.heroEditRequest.setBirthdate(null);
        violations = validator.validate(this.heroEditRequest);
        assertIsNotEmpty(violations);

    }

    private HeroEditRequest validInstance() {
        return new HeroEditRequest(1L,
                "Backend Developer",
                0,
                3500,
                100,
                Power.USE_OF_AN_ELEMENT.name(),
                new Date()
        );
    }

    public static void assertIsEmpty(final Set<ConstraintViolation<HeroEditRequest>> set) {
        assertTrue(set.isEmpty());
    }

    public static void assertIsNotEmpty(final Set<ConstraintViolation<HeroEditRequest>> set) {
        assertFalse(set.isEmpty());
    }

}
