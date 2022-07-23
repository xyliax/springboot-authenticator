package com.auth.logincontroller;

import com.auth.loginservice.UserService;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private InfoWrapper<String> stringInfoWrapper;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> register
            (@RequestBody User user) {
        ServiceSegment info = userService.userRegister(user);
        return stringInfoWrapper.wrap(info);
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login
            (@RequestParam("type") String type,
             @RequestParam("identifier") String identifier,
             @RequestParam("password") String password) {
        ServiceSegment info = userService.userLogin(identifier, password, type);
        return stringInfoWrapper.wrap(info);
    }
}
