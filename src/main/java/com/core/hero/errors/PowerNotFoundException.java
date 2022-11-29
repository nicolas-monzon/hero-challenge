package com.core.hero.errors;

public class PowerNotFoundException extends CustomException {

    private static final long serialVersionUID = 8551817304771092595L;
    private static final String DESCRIPTION = "Power not found";

    public PowerNotFoundException() {
        super(DESCRIPTION);
    }

}