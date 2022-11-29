package com.core.hero.errors.db;

import com.core.hero.errors.CustomException;
import lombok.NonNull;

public class SaveInstanceException extends CustomException {

    private static final long serialVersionUID = -2165594721493762732L;
    private static final String DESCRIPTION = "DB Error - Save instance";

    public SaveInstanceException(@NonNull final String clazzName) {
        super(getMessage(clazzName).toString());
    }

    private static StringBuilder getMessage(@NonNull final String detail) {
        final StringBuilder s = new StringBuilder(DESCRIPTION);
        s.append(". ").append(detail);
        return s;
    }

}
