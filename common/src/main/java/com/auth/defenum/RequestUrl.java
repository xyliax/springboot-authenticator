package com.auth.defenum;

public enum RequestUrl {
    LOGIN_SERVER("(/login-server/.*)|(/login/.*)"),
    AUTH_SERVER("(/auth-server/.*)|(/auth/.*)"),
    CONTENT_SERVER("(/content-server/.*)|(/content/.*)"),
    REGISTER("(/login-server/user)"),
    USER("(/content-server/archive/user)|(/content-server/course)"),
    FREE("(/login-server/user)");

    public String path;

    RequestUrl(String path) {
        this.path = path;
    }

    public static String serverUrl() {
        RequestUrl[] requestUrls = {LOGIN_SERVER, AUTH_SERVER, CONTENT_SERVER};
        StringBuilder stringBuilder = new StringBuilder();
        for (RequestUrl requestUrl : requestUrls)
            stringBuilder.append(requestUrl.path).append("|");
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public void addUrl(RequestUrl requestUrl, String url) {
        requestUrl.path += String.format("|(%s)", url);
    }
}
