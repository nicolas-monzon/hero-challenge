package com.core.hero.errors;

import com.core.hero.controller.payload.ErrorResponse;
import com.core.hero.errors.db.DeleteInstanceException;
import com.core.hero.errors.db.GetInstanceException;
import com.core.hero.errors.db.SaveInstanceException;
import com.core.hero.errors.http.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            DeleteInstanceException.class,
            GetInstanceException.class,
            SaveInstanceException.class
    })
    @ResponseBody
    public ErrorResponse dbError(final CustomException exception) {
        return new ErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class
    })
    @ResponseBody
    public ErrorResponse notFoundRequest(final NotFoundException exception) {
        return new ErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            PowerNotFoundException.class
    })
    @ResponseBody
    public ErrorResponse powerNotFoundRequest(final PowerNotFoundException exception) {
        return new ErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseBody
    public ErrorResponse badRequest(final Exception ex) {
        return new ErrorResponse(ex); // Possible improvement: Maybe it would be more accurate to indicate the field.
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ErrorResponse exception(final Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(exception);
    }

}