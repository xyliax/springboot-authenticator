package com.auth.loginserver.controller;

import com.auth.loginserver.service.UserService;
import com.auth.model.InfoWrapper;
import com.auth.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource(name = "stringInfoWrapper")
    private InfoWrapper<String> stringInfoWrapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String info = userService.userRegister(user);
        return stringInfoWrapper.wrap(info);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String info = userService.userLogin(user);
        return stringInfoWrapper.wrap(info);
    }

}
