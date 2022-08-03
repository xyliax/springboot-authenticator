package com.auth.defenum;

public enum Role {
    SUPERUSER, ADMIN, VISITOR, UNKNOWN, UNDEFINED;


    public static Role[] viewer() {
        return new Role[]{SUPERUSER, ADMIN, VISITOR};
    }

    public static Role[] editor() {
        return new Role[]{SUPERUSER, ADMIN};
    }

    public static Role parse(String roleStr) {
        try {
            return Role.valueOf(roleStr);
        } catch (IllegalArgumentException illegalArgumentException) {
            return UNDEFINED;
        }
    }
}