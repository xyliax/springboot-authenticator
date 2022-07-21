package com.auth.eurekaconsumer.controller;


import com.auth.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class consumerController {
    @Resource
    private RestTemplate restTemplate;

    @PutMapping("/login-server/user")
    public ResponseEntity<String> registerAtLoginServer(@RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user/register";
        return restTemplate.postForEntity(url, user, String.class);
    }

    @PostMapping("/login-server/user")
    public ResponseEntity<String> loginAtLoginServer
            (@RequestHeader("Type") String type,
             @RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user/login";
        HttpHeaders header = new HttpHeaders();
        header.add("Type", type);
        HttpEntity<User> userEntity = new HttpEntity<>(user, header);
        return restTemplate.postForEntity(url, userEntity, String.class);
    }
}
