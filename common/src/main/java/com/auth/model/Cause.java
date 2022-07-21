package com.auth.model;


public enum Cause {
    UNKNOWN("600"),
    DUP_NAME("418"),
    NO_RESULT("419"),
    MISMATCH("420");
    private static final String prefix = "#!ERRNO";
    public final String code;

    Cause(String code) {
        this.code = prefix + code;
    }

    public static boolean isCause(Object code) {
        if (!(code instanceof String))
            return false;
        return ((String) code).startsWith(prefix);
    }
}
