package com.auth.eurekaconsumer;

public enum ServUrl {
    LOGIN("login-server"),
    AUTH("auth-server"),
    CONTENT("content-server");
    private static final String prefix = "http://";
    public final String url;

    ServUrl(String url) {
        this.url = prefix + url;
    }
}
