package com.auth.eurekaconsumer.controller;


import com.auth.model.User;
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
    public Object registerAtLoginServer(@RequestBody User user) {
        String url = "http://login-server/user/register";
        return restTemplate.postForObject(url, user, String.class);
    }
}
