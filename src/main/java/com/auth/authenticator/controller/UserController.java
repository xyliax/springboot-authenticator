package com.auth.authenticator.controller;

import com.auth.authenticator.service.MicroServiceUrl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private MicroServiceUrl microServiceUrl;

    @GetMapping("/testUrl")
    public String testUrl() {
        return String.format("login:%s auth:%s content:%s",
                microServiceUrl.getLoginServerUrl(),
                microServiceUrl.getAuthServerUrl(),
                microServiceUrl.getContentServerUrl());
    }
}
