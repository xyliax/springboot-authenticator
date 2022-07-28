package com.auth.eurekacontroller;

import com.auth.authcontroller.AuthServerController;
import com.auth.model.User;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping
public class consumerController {
    @Resource
    private RestTemplate restTemplate;

    @PutMapping("/login-server/user")
    public ResponseEntity<String> registerAtLoginServer(
            @RequestBody User user) {
        String url = ServUrl.LOGIN.url + "/user";
        return restTemplate.postForEntity(url, user, String.class);
    }

    @PostMapping("/login-server/user")
    public ResponseEntity<String> loginAtLoginServer(
            @RequestHeader("type") String type,
            @RequestBody Map<String, String> idPair) {
        String identifier = idPair.get(type);
        String password = idPair.get("password");
        String url = ServUrl.LOGIN.url + "/user?type={?}&identifier={?}&password={?}";
        return restTemplate.getForEntity(url, String.class, type, identifier, password);
    }

    @GetMapping("/auth-server/user")
    public ResponseEntity<User> getUser(
            @RequestParam("type") String type,
            @RequestParam("identifier") String identifier) {
        String url = ServUrl.AUTH.url + "/user?type={?}&identifier={?}";
        return restTemplate.getForEntity(url, User.class, type, identifier);
    }

    /**
     * @param role
     * @param courseId
     */
    @GetMapping("/auth-server/user-list")
    public ResponseEntity<User[]> getUserList(
            @RequestParam("role") String role,
            @RequestParam("course") String courseId) {
        String url = ServUrl.AUTH.url + "/user-list?role={?}&course={?}";
        return restTemplate.getForEntity(url, User[].class, role, courseId);
    }

    /**
     * @param role
     * @param action
     * @param idPair
     * @see AuthServerController#assign(String, Boolean, Pair)
     */
    @PostMapping("/auth-server/auth")
    public ResponseEntity<String> assignAuth(
            @RequestParam("role") String role,
            @RequestParam("action") Boolean action,
            @RequestBody Pair<String, String> idPair) {
        String url = ServUrl.AUTH.url + "/edit/auth?action={?}";
        return restTemplate.postForEntity(url, idPair, String.class, role, action);
    }
}
