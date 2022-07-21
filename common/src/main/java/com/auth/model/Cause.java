package com.auth.model;

public enum Cause {
    SUCCESS("checked"),
    UNKNOWN("600"),
    DUP_NAME("418"),
    NO_RESULT("419"),
    MISMATCH("420"),
    UNDEF_ARG("421");

    public final String code;

    Cause(String code) {
        this.code = code;
    }

    public static boolean isFailure(Cause cause) {
        return cause != SUCCESS;
    }
}
