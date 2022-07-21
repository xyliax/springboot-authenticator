package com.auth.eurekaconsumer.controller;


import com.auth.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class consumerController {
    @Resource
    private RestTemplate restTemplate;

    @PutMapping("/login-server/user")
    public ResponseEntity<String> registerAtLoginServer
            (@RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user/register";
        return restTemplate.postForEntity(url, user, String.class);
    }

    @PostMapping("/login-server/user")
    public ResponseEntity<String> loginAtLoginServer
            (@RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user/login";
        return restTemplate.postForEntity(url, user, String.class);
    }
}
