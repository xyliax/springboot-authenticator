package com.auth.authcontroller;

import com.auth.authservice.AuthService;
import com.auth.model.User;
import com.auth.util.InfoWrapper;
import com.auth.util.ServiceSegment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthServerController {
    @Resource
    private AuthService authService;
    @Resource
    private InfoWrapper infoWrapper;

    @GetMapping(path = "/user")
    public ResponseEntity<User> user(
            @RequestParam("type") String type,
            @RequestParam("identifier") String identifier) {

        ServiceSegment info = authService.getUserDetails(identifier, type);
        return infoWrapper.wrap(info, User.class);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> delete(
            @RequestParam("user") String userId) {

        ServiceSegment info = authService.deleteUser(userId);
        return infoWrapper.wrap(info, User.class);
    }

    @GetMapping(path = "/user/user-list")
    public ResponseEntity<User[]> userList(
            @RequestParam("roleGroup") String roleGroup,
            @RequestParam("course") String courseId) {

        ServiceSegment info;
        if ("*".equals(courseId) && "*".equals(roleGroup))
            info = authService.getUserList();
        else
            info = authService.getUserListByRole(courseId, roleGroup);
        return infoWrapper.wrap(info, User[].class);
    }

    @PostMapping(path = "/edit/auth")
    public ResponseEntity<User> assign(
            @RequestParam("user") String userId,
            @RequestBody Map<String, String>[] idMapArray) {

        ServiceSegment info = authService.assignAuth(userId, idMapArray);
        return infoWrapper.wrap(info, User.class);
    }
}
