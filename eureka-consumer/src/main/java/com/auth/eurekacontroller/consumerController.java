package com.auth.eurekacontroller;

import com.auth.authcontroller.AuthServerController;
import com.auth.contentcontroller.ContentServerController;
import com.auth.dao.LocalFileRepository;
import com.auth.defenum.Role;
import com.auth.logincontroller.LoginServerController;
import com.auth.model.Archive;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.model.User;
import org.springframework.http.HttpStatus;
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
    @Resource
    private LocalFileRepository localFileRepository;

    /**
     * @see LoginServerController#register(boolean, User)
     */
    @PutMapping(path = "/login-server/user")
    public ResponseEntity<User> register(
            @RequestParam("register") boolean register,
            @RequestBody User user) {

        String url = ServUrl.LOGIN.url + "/user?register={?}";
        return restTemplate.postForEntity(url, user, User.class, register);
    }

    /**
     * @param type   identifier type, e,g. "username"
     * @param idPair {'identifier', password}
     * @see LoginServerController#login(String, String, String)
     */
    @PostMapping(path = "/login-server/user")
    public ResponseEntity<User> login(
            @RequestHeader("type") String type,
            @RequestBody Map<String, String> idPair) {

        String identifier = idPair.get(type);
        String password = idPair.get("password");
        String url = ServUrl.LOGIN.url + "/user?type={?}&identifier={?}&password={?}";
        return restTemplate.getForEntity(url, User.class, type, identifier, password);
    }

    /**
     * @param type       access type. e.g, "username"
     * @param identifier access string. e.g, username
     * @see AuthServerController#user(String, String)
     */
    @GetMapping(path = "/auth-server/user")
    public ResponseEntity<User> getUser(
            @RequestParam("type") String type,
            @RequestParam("identifier") String identifier) {

        String url = ServUrl.AUTH.url + "/user?type={?}&identifier={?}";
        return restTemplate.getForEntity(url, User.class, type, identifier);
    }

    /**
     * @see AuthServerController#delete(String)
     */
    @DeleteMapping(path = "/auth-server/user")
    public ResponseEntity<User> deleteUser(
            @RequestParam("userId") String userId) {

        String url = ServUrl.AUTH.url + "/user?user={?}";
        return restTemplate.postForEntity(url, null, User.class, userId);
    }

    /**
     * Get an array of Users having a Role for a Course.
     * <br>
     * roleGroup and courseId can be either all "*" or no "*"
     * </br>
     * @param roleGroup "viewer" or "editor", "*" means "all"
     * @param courseId  courseId, "*" means "all"
     * @see Role
     * @see AuthServerController#userList(String, String)
     */
    @GetMapping(path = "/auth-server/user-list")
    public ResponseEntity<User[]> getUserList(
            @RequestParam("roleGroup") String roleGroup,
            @RequestParam("courseId") String courseId) {

        String url = ServUrl.AUTH.url + "/user/user-list?roleGroup={?}&course={?}";
        return restTemplate.getForEntity(url, User[].class, roleGroup, courseId);
    }

    /**
     * Assign or Un-assign a User of Role permission to a Course
     * @param userId userId
     * @param idMap  {courseId, role, action}[]
     * @see AuthServerController#assign(String, Map[])
     */
    @PostMapping(path = "/auth-server/auth")
    public ResponseEntity<User> assignAuth(
            @RequestParam("userId") String userId,
            @RequestBody Map<String, String>[] idMap) {

        String url = ServUrl.AUTH.url + "/edit/auth?user={?}";
        return restTemplate.postForEntity(url, idMap, User.class, userId);
    }

    // TODO: 4/8/2022 分发证书
//    @PostMapping(path = "/auth-server/cert")
//    public ResponseEntity<Cert> getCert() {
//        return null;
//    }

    /**
     * @see ContentServerController#register(Course)
     */
    @PutMapping(path = "/content-server/course")
    public ResponseEntity<Course> createCourse(
            @RequestBody Course course) {

        String url = ServUrl.CONTENT.url + "/course";
        return restTemplate.postForEntity(url, course, Course.class);
    }

    /**
     * @see ContentServerController#course(String)
     */
    @GetMapping(path = "/content-server/course")
    public ResponseEntity<Course> getCourse(
            @RequestParam("courseId") String courseId) {

        String url = ServUrl.CONTENT.url + "/course?course={?}";
        return restTemplate.getForEntity(url, Course.class, courseId);
    }

    /**
     * @see ContentServerController#deleteCourse(String)
     */
    @DeleteMapping(path = "/content-server/course")
    public ResponseEntity<Course> deleteCourse(
            @RequestParam("courseId") String courseId) {

        String url = ServUrl.CONTENT.url + "/edit/course?course={?}";
        return restTemplate.postForEntity(url, null, Course.class, courseId);
    }

    /**
     * @param userId userId, "*" means "all"
     * @see ContentServerController#courseList(String)
     */
    @GetMapping(path = "/content-server/course/course-list")
    public ResponseEntity<Course[]> getCourseList(
            @RequestParam("userId") String userId) {

        String url = ServUrl.CONTENT.url + "/course/course-list?user={?}";
        return restTemplate.getForEntity(url, Course[].class, userId);
    }

    /**
     * upload & link a CourseFile with a Course
     * @see ContentServerController#uploadFile(CourseFile)
     */
    @PutMapping(path = "/content-server/file")
    public ResponseEntity<CourseFile> uploadFile(
            @RequestParam("courseId") String courseId,
            @RequestPart("description") String description,
            @RequestPart("file") MultipartFile multipartFile) {

        String url = ServUrl.CONTENT.url + "/file/upload";
        CourseFile courseFile = localFileRepository.createCourseFile(multipartFile, courseId, description);
        try {
            ResponseEntity<CourseFile> response = restTemplate.
                    postForEntity(url, courseFile, CourseFile.class);
            if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE)
                localFileRepository.deleteCourseFile(courseFile);
            return response;
        } catch (RuntimeException runtimeException) {
            localFileRepository.deleteCourseFile(courseFile);
            return null;
        }
    }

    /**
     * @see ContentServerController#deleteFile(String, String)
     */
    @DeleteMapping(path = "/content-server/file")
    public ResponseEntity<CourseFile> deleteFile(
            @RequestParam("courseId") String courseId,
            @RequestParam("fileId") String fileId) {

        String url = ServUrl.CONTENT.url + "/file/delete?course={?}&file={?}";
        return restTemplate.postForEntity(url, null, CourseFile.class, courseId, fileId);
    }

    /**
     * @see ContentServerController#uploadFileNextCloud(CourseFile)
     */
    @PutMapping(path = "/content-server/file/next-cloud")
    public ResponseEntity<CourseFile> uploadFileNextCloud(
            @RequestBody CourseFile courseFile) {

        String url = ServUrl.CONTENT.url + "/file/upload/next-cloud";
        return restTemplate.postForEntity(url, courseFile, CourseFile.class);
    }

    /**
     * @see ContentServerController#deleteFileNextCloud(String, String)
     */
    @DeleteMapping(path = "/content-server/file/next-cloud")
    public ResponseEntity<CourseFile> deleteFileNextCloud(
            @RequestParam("courseId") String courseId,
            @RequestParam("fileId") String fileId) {

        String url = ServUrl.CONTENT.url + "/file/next-cloud/delete?course={?}&file={?}";
        return restTemplate.postForEntity(url, null, CourseFile.class, courseId, fileId);
    }

    /**
     * @see ContentServerController#makeArchive(Archive)
     */
    @PutMapping(path = "/content-server/archive")
    public ResponseEntity<Archive> createArchive(
            @RequestBody Archive archive) {

        String url = ServUrl.CONTENT.url + "/archive";
        return restTemplate.postForEntity(url, archive, Archive.class);
    }

    /**
     * @see ContentServerController#archive(String)
     */
    @GetMapping(path = "/content-server/archive")
    public ResponseEntity<Archive> getArchive(
            @RequestParam(value = "archiveId", required = false) String archiveId) {

        String url = ServUrl.CONTENT.url + "/archive?archive={?}";
        return restTemplate.getForEntity(url, Archive.class, archiveId);
    }

    /**
     * @see ContentServerController#deleteArchive(boolean, String)
     */
    @DeleteMapping(path = "/content-server/archive")
    public ResponseEntity<Archive> deleteArchive(
            @RequestParam("delete") boolean delete,
            @RequestParam("archiveId") String archiveId) {

        String url = ServUrl.CONTENT.url + "/archive/delete?delete={?}&archive={?}";
        return restTemplate.postForEntity(url, null, Archive.class, delete, archiveId);
    }

    /**
     * @param idMap {courseId, action}
     * @see ContentServerController#archiveCourse(String, Map[])
     */
    @PostMapping(path = "/content-server/archive/edit")
    public ResponseEntity<Archive> archiveCourse(
            @RequestParam("archiveId") String archiveId,
            @RequestBody Map<String, String>[] idMap) {

        String url = ServUrl.CONTENT.url + "/archive/edit?archive={?}";
        return restTemplate.postForEntity(url, idMap, Archive.class, archiveId);
    }

    /**
     * @see ContentServerController#archiveList(String)
     */
    @GetMapping(path = "/content-server/archive/user")
    public ResponseEntity<Archive> getUserArchive(
            @RequestParam("userId") String userId) {

        String url = ServUrl.CONTENT.url + "/archive/user?user={?}";
        return restTemplate.getForEntity(url, Archive.class, userId);
    }
}
