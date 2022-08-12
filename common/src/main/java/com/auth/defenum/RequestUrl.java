package com.auth.defenum;

public enum RequestUrl {
    LOGIN_SERVER("(/login-server/.*)|(/login/.*)"),
    AUTH_SERVER("(/auth-server/.*)|(/auth/.*)"),
    CONTENT_SERVER("(/content-server/.*)|(/content/.*)");

    public final String path;

    RequestUrl(String path) {
        this.path = path;
    }
}
