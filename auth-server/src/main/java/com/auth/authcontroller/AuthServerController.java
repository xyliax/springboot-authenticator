package com.auth.authcontroller;

import com.auth.authservice.AuthService;
import com.auth.model.ServiceSegment;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class AuthServerController {
    @Resource
    private AuthService authService;
    @Resource
    private InfoWrapper infoWrapper;

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> details(
            @RequestParam("identifier") String identifier,
            @RequestParam("type") String type) {

        ServiceSegment info = authService.getUserDetails(identifier, type);
        return infoWrapper.wrap(info, User.class);
    }

    @GetMapping(path = "/user-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User[]> userList(
            @RequestParam("role") String role,
            @RequestParam("course") String courseId) {

        ServiceSegment info;
        if ("*".equals(courseId))
            info = authService.getUserListByRole(role);
        else
            info = authService.getUserListByAuth(courseId, role);
        return infoWrapper.wrap(info, User[].class);
    }

    @PostMapping(path = "/edit/auth", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> assign(
            @RequestParam("role") String role,
            @RequestParam("action") Boolean action,
            @RequestBody Map<String, String> idMap) {

        String userId = idMap.get("userId");
        String courseId = idMap.get("courseId");
        ServiceSegment info = authService.assignAuth(userId, courseId, role, action);
        return infoWrapper.wrap(info, String.class);
    }
}
