package com.auth.loginserver.controller;

import com.auth.loginserver.service.UserService;
import com.auth.model.User;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public String login(@RequestBody User user) {
        return "Trying to login at LoginServer";
    }
}
