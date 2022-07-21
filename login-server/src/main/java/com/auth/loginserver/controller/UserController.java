package com.auth.loginserver.controller;

import com.auth.loginserver.service.UserService;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import com.auth.utilities.InfoWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private InfoWrapper<String> stringInfoWrapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        ServiceSegment info = userService.userRegister(user);
        return stringInfoWrapper.wrap(info);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login
            (@RequestHeader("Type") String type,
             @RequestBody User user) {
        ServiceSegment info = userService.userLogin(user, type);
        return stringInfoWrapper.wrap(info);
    }

}
