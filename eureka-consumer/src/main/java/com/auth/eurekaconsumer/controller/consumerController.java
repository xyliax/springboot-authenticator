package com.auth.eurekaconsumer.controller;


import com.auth.eurekaconsumer.ServUrl;
import com.auth.model.Cause;
import com.auth.model.User;
import org.springframework.http.HttpStatus;
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
        String info = restTemplate.postForObject(url, user, String.class);
        HttpStatus status = Cause.isCause(info) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK;
        return new ResponseEntity<>(info, status);
    }

    @PostMapping("/login-server/user")
    public ResponseEntity<String> loginAtLoginServer(@RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user/login";
        String info = restTemplate.postForObject(url, user, String.class);
        HttpStatus status = Cause.isCause(info) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.OK;
        return new ResponseEntity<>(info, status);
    }
}
