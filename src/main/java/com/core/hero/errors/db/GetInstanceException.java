package com.core.hero.errors.db;

import com.core.hero.errors.CustomException;
import lombok.NonNull;

public class GetInstanceException extends CustomException {

    private static final long serialVersionUID = 6486632509155813435L;
    private static final String DESCRIPTION = "DB Error - Get instance";

    public GetInstanceException(@NonNull final String clazzName) {
        super(getMessage(clazzName).toString());
    }

    private static StringBuilder getMessage(@NonNull final String detail) {
        final var s = new StringBuilder(DESCRIPTION);
        s.append(". ").append(detail);
        return s;
    }

}
