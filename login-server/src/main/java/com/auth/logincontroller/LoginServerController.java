package com.auth.logincontroller;

import com.auth.loginservice.LoginService;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import com.auth.util.ServiceSegment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginServerController {
    @Resource
    private LoginService userService;
    @Resource
    private InfoWrapper infoWrapper;

    @PostMapping(path = "/user")
    public ResponseEntity<User> register(
            @RequestParam("register") boolean register,
            @RequestBody User user) {

        ServiceSegment info = userService.userRegister(user, register);
        return infoWrapper.wrap(info, User.class);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<User> login(
            @RequestParam("type") String type,
            @RequestParam("identifier") String identifier,
            @RequestParam("password") String password) {

        ServiceSegment info = userService.userLogin(identifier, password, type);
        return infoWrapper.wrap(info, User.class);
    }
}
