package com.core.hero.errors;

import com.core.hero.controller.payload.Empty;
import com.core.hero.controller.payload.ErrorResponse;
import com.core.hero.errors.db.DeleteInstanceException;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.errors.db.SaveInstanceException;
import com.core.hero.errors.http.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ApiExceptionHandlerTest {

    private static final String clazz = "Example";

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    public void before() {
        this.apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    @DisplayName("Should check database errors")
    public void shouldCheckDbErrors() {
        DeleteInstanceException deleteInstanceException = new DeleteInstanceException(clazz);
        ErrorResponse errorResponse = this.apiExceptionHandler.dbError(deleteInstanceException);
        assertEquals("DeleteInstanceException", errorResponse.getError());
        assertEquals("DeleteInstance", errorResponse.getCode());
        assertTrue(errorResponse.getDescription().contains(clazz));

        GetInstanceException getInstanceException = new GetInstanceException(clazz);
        errorResponse = this.apiExceptionHandler.dbError(getInstanceException);
        assertEquals("GetInstanceException", errorResponse.getError());
        assertEquals("GetInstance", errorResponse.getCode());
        assertTrue(errorResponse.getDescription().contains(clazz));

        SaveInstanceException saveInstanceException = new SaveInstanceException(clazz);
        errorResponse = this.apiExceptionHandler.dbError(saveInstanceException);
        assertEquals("SaveInstanceException", errorResponse.getError());
        assertEquals("SaveInstance", errorResponse.getCode());
        assertTrue(errorResponse.getDescription().contains(clazz));
    }

    @Test
    @DisplayName("Should check not found status")
    public void shouldCheckNotFoundErrors() {
        NotFoundException notFoundException = new NotFoundException(clazz);
        ErrorResponse errorResponse = this.apiExceptionHandler.notFoundRequest(notFoundException);
        assertEquals("NotFoundException", errorResponse.getError());
        assertEquals("NotFound", errorResponse.getCode());
        assertFalse(errorResponse.getDescription().isEmpty());
    }

    @Test
    @DisplayName("Should check if power not found")
    public void shouldCheckIfPowerNotFound() {
        PowerNotFoundException powerNotFoundException = new PowerNotFoundException();
        ErrorResponse errorResponse = this.apiExceptionHandler.powerNotFoundRequest(powerNotFoundException);
        assertEquals("PowerNotFoundException", errorResponse.getError());
        assertEquals("PowerNotFound", errorResponse.getCode());
        assertEquals("Power not found", errorResponse.getDescription());
    }

    @Test
    @DisplayName("Should check bad request status")
    public void shouldCheckBadRequestError() throws NoSuchMethodException {
        ConstraintViolationException constraintViolationException = new ConstraintViolationException(new HashSet<>());
        ErrorResponse errorResponse = this.apiExceptionHandler.badRequest(constraintViolationException);
        assertEquals("ConstraintViolationException", errorResponse.getError());
        assertNotNull(errorResponse.getCode());
        assertNotNull(errorResponse.getDescription());

        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException =
                new MethodArgumentTypeMismatchException("",
                        Empty.class,
                        "name",
                        new MethodParameter(getClass().getMethod("shouldCheckBadRequestError"), -1),
                        new IllegalArgumentException());
        errorResponse = this.apiExceptionHandler.badRequest(methodArgumentTypeMismatchException);
        assertEquals("MethodArgumentTypeMismatchException", errorResponse.getError());
        assertNotNull(errorResponse.getCode());
        assertNotNull(errorResponse.getDescription());
    }

    @Test
    @DisplayName("Should check other exception")
    public void shouldCheckOtherException() {
        NullPointerException nullPointerException = new NullPointerException();
        ErrorResponse errorResponse = this.apiExceptionHandler.exception(nullPointerException);
        assertEquals("NullPointerException", errorResponse.getError());
        assertNotNull(errorResponse.getCode());
        assertNull(errorResponse.getDescription());
    }

}
