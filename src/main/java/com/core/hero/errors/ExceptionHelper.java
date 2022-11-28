package com.core.hero.errors;

public class ExceptionHelper {

    private static final String EXCEPTION = "Exception";
    private static final String EMPTY_STRING = "";

    public static String getSubName(final Exception exception) {
        return exception.getClass().getSimpleName().replace(EXCEPTION, EMPTY_STRING);
    }

}