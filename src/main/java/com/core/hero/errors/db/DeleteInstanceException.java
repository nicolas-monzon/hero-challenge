package com.core.hero.errors.db;

import com.core.hero.errors.CustomException;
import lombok.NonNull;

public class DeleteInstanceException extends CustomException {

    private static final long serialVersionUID = 8451817304771092595L;
    private static final String DESCRIPTION = "DB Error - Delete instance";

    public DeleteInstanceException(@NonNull final String clazzName) {
        super(getMessage(clazzName).toString());
    }

    private static StringBuilder getMessage(@NonNull final String clazzName) {
        final var s = new StringBuilder(DESCRIPTION);
        s.append(". ").append(clazzName);
        return s;
    }

}