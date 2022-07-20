package com.auth.loginserver.controller;

import com.auth.loginserver.service.UserService;
import com.auth.model.User;
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

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.userRegister(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.userLogin(user);
    }
}
