package com.auth.eurekacontroller;

public enum ServUrl {
    LOGIN("login-server/login"),
    AUTH("auth-server/auth"),
    CONTENT("content-server/content");
    private static final String prefix = "http://";
    public final String url;

    ServUrl(String url) {
        this.url = prefix + url;
    }
}
