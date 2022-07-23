package com.auth.authcontroller;

import com.auth.authservice.UserService;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private InfoWrapper<User> userInfoWrapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> details
            (@RequestParam("identifier") String identifier,
             @RequestParam("type") String type) {
        ServiceSegment info = userService.getUserDetails(identifier, type);
        return userInfoWrapper.wrap(info);
    }
}
