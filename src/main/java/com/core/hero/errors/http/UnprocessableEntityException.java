package com.core.hero.errors.http;

import com.core.hero.errors.CustomException;
import lombok.NonNull;

public class UnprocessableEntityException extends CustomException {

    private static final long serialVersionUID = 1172629328346735324L;
    private static final String DESCRIPTION = "Unprocessable entity Exception - 422";

    public UnprocessableEntityException(@NonNull final String message) {
        super(DESCRIPTION, message);
    }

}