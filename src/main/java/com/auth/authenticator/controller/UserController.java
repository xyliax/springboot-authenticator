package com.auth.authenticator.controller;

import com.auth.authenticator.service.AuthService;
import com.auth.authenticator.service.ContentService;
import com.auth.authenticator.service.LoginService;
import com.auth.authenticator.service.MicroServiceUrl;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private MicroServiceUrl microServiceUrl;
    @Resource
    private LoginService loginService;
    @Resource
    private AuthService authService;
    @Resource
    private ContentService contentService;

    @PutMapping("/user")
    public String testUrl() {
        return String.format("login:%s auth:%s content:%s",
                microServiceUrl.getLoginServerUrl(),
                microServiceUrl.getAuthServerUrl(),
                microServiceUrl.getContentServerUrl());
    }
}
