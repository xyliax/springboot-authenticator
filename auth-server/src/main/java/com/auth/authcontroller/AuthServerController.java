package com.auth.authcontroller;

import com.auth.authservice.AuthService;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class AuthServerController {
    @Resource
    private AuthService authService;
    @Resource
    private InfoWrapper<String> stringInfoWrapper;
    @Resource
    private InfoWrapper<User> userInfoWrapper;
    @Resource
    private InfoWrapper<User[]> userArrayInfoWrapper;

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> details(
            @RequestParam("identifier") String identifier,
            @RequestParam("type") String type) {
        ServiceSegment info = authService.getUserDetails(identifier, type);
        return userInfoWrapper.wrap(info);
    }

    @GetMapping(path = "user-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User[]> userList(
            @RequestParam("role") String role,
            @RequestParam("course") String courseId) {
        ServiceSegment info;
        if ("*".equals(courseId))
            info = authService.getUserListByRole(role);
        else
            info = authService.getUserListByAuth(courseId, role);
        return userArrayInfoWrapper.wrap(info);
    }

    @PostMapping(path = "/edit/auth", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> assign(
            @RequestParam("role") String role,
            @RequestParam("action") Boolean action,
            @RequestBody Pair<String, String> idPair) {
        ServiceSegment info = authService.
                assignAuth(idPair.getFirst(), idPair.getSecond(), role, action);
        return stringInfoWrapper.wrap(info);
    }
}
