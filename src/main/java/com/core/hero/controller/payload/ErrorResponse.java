package com.core.hero.controller.payload;

import com.core.hero.errors.ExceptionHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String error;
    private final String description;
    private final String code;

    public ErrorResponse(Exception exception) {
        this.error = exception.getClass().getSimpleName();
        this.description = exception.getMessage();
        this.code = ExceptionHelper.getSubName(exception);
    }

}