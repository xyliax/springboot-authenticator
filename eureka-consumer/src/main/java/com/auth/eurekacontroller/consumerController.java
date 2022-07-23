package com.auth.eurekacontroller;


import com.auth.model.User;
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
}
