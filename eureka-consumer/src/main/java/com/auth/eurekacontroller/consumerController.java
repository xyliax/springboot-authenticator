package com.auth.eurekacontroller;

import com.auth.authcontroller.AuthServerController;
import com.auth.logincontroller.LoginServerController;
import com.auth.model.CourseFile;
import com.auth.model.User;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @param type   identifier type, e,g. "username"
     * @param idPair {'identifier', password}
     * @see LoginServerController#login(String, String, String)
     */
    @PostMapping(value = "/login-server/user")
    public ResponseEntity<String> loginAtLoginServer(
            @RequestHeader("type") String type,
            @RequestBody Map<String, String> idPair) {

        String identifier = idPair.get(type);
        String password = idPair.get("password");
        String url = ServUrl.LOGIN.url + "/user?type={?}&identifier={?}&password={?}";
        return restTemplate.getForEntity(url, String.class, type, identifier, password);
    }

    /**
     * @param type       access type. e.g, "username"
     * @param identifier access string. e.g, username
     * @see AuthServerController#details(String, String)
     */
    @GetMapping(path = "/auth-server/user")
    public ResponseEntity<User> getUser(
            @RequestHeader("type") String type,
            @RequestParam("identifier") String identifier) {

        String url = ServUrl.AUTH.url + "/user?type={?}&identifier={?}";
        return restTemplate.getForEntity(url, User.class, type, identifier);
    }

    /**
     * Get an array of Users having a Role for a Course
     * @param role     Role.toString(), "*" means "all"
     * @param courseId courseId, "*" means "all"
     * @see AuthServerController#userList(String, String)
     */
    @GetMapping(path = "/auth-server/user-list")
    public ResponseEntity<User[]> getUserList(
            @RequestParam("role") String role,
            @RequestParam("course") String courseId) {

        String url = ServUrl.AUTH.url + "/user-list?role={?}&course={?}";
        return restTemplate.getForEntity(url, User[].class, role, courseId);
    }

    /**
     * Assign or Un-assign a User of Role permission to a Course
     * @param action true(assign), false(un-assign)
     * @param idMap  {userId, courseId, role}
     * @see AuthServerController#assign(String, Boolean, Map)
     */
    @PostMapping(path = "/auth-server/auth")
    public ResponseEntity<String> assignAuth(
            @RequestParam("action") Boolean action,
            @RequestBody Map<String, String> idMap) {

        String role = idMap.get("role");
        String url = ServUrl.AUTH.url + "/edit/auth?role={?}&action={?}";
        return restTemplate.postForEntity(url, idMap, String.class, role, action);
    }

    @SneakyThrows
    @PostMapping(path = "/content-server/file/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("course") String courseId,
            @RequestPart("file") MultipartFile multipartFile) {

        String url = ServUrl.CONTENT.url + "/file/upload?course={?}";
        String description = "This is a file!";
        CourseFile courseFile = new CourseFile(multipartFile, description);
        return restTemplate.postForEntity(url, courseFile, String.class, courseId);
    }

    @GetMapping(path = "/content-server/file/download")
    public ResponseEntity<CourseFile> downloadFile(
            @RequestParam("file") String fileId) {

        String url = ServUrl.CONTENT.url + "/file/download?file={?}";
        return restTemplate.getForEntity(url, CourseFile.class, fileId);
    }
}
