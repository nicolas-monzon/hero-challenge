package com.core.hero.errors;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomException extends RuntimeException {

    private static final long serialVersionUID = 737045365296041689L;
    private static final String END_LINE = ". ";

    @Getter
    private final String code;

    private boolean alreadyLogged;

    public CustomException(@NonNull final String code) {
        super(code);
        this.code = code;
        this.alreadyLogged = false;
    }

    public CustomException(@NonNull final String code, @NonNull final String message) {
        super(message);
        this.code = code;
        this.alreadyLogged = false;
    }

    public void logException() {
        if (mustLogException()) {
            if (!this.alreadyLogged) {
                this.alreadyLogged = true;
                log.error(getMessage());
            }
        }
    }

    public static @NonNull StringBuilder getMessage(@NonNull final String detail, @NonNull final String description) {
        final StringBuilder s = new StringBuilder(description);
        s.append(END_LINE).append(detail);
        return s;
    }

    public static @NonNull StringBuilder getMessage(
            @NonNull final String format,
            @NonNull final String field,
            @NonNull final String description
    ) {
        final StringBuilder s = new StringBuilder(description);
        s.append(END_LINE).append(String.format(format, field));
        return s;
    }

    public static @NonNull StringBuilder getMessage(@NonNull final String format,
                                                    @NonNull final String uuid,
                                                    @NonNull final String clazz,
                                                    @NonNull final String description) {
        final StringBuilder s = new StringBuilder(description);
        s.append(END_LINE).append(String.format(format, uuid, clazz));
        return s;
    }

    protected abstract boolean mustLogException();

}
