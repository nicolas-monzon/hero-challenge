package com.core.hero.errors.http;

import com.core.hero.errors.CustomException;
import lombok.NonNull;

public class NotFoundException extends CustomException {

    private static final long serialVersionUID = 2120036918380922021L;
    private static final String DESCRIPTION = "Not Found Exception";

    public NotFoundException(@NonNull final String message) {
        super(DESCRIPTION, message);
    }

    @Override
    protected boolean mustLogException() {
        return true;
    }

}
