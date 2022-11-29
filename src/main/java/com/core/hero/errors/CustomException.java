package com.core.hero.errors;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomException extends RuntimeException {

    private static final long serialVersionUID = 737045365296041689L;

    @Getter
    private final String code;

    public CustomException(@NonNull final String code) {
        super(code);
        this.code = code;
    }

    public CustomException(@NonNull final String code, @NonNull final String message) {
        super(message);
        this.code = code;
    }

}
