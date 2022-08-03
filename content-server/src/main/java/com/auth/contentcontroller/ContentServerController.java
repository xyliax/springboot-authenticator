package com.auth.contentcontroller;

import com.auth.contentservice.ContentService;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.util.InfoWrapper;
import com.auth.util.ServiceSegment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ContentServerController {
    @Resource
    private ContentService contentService;
    @Resource
    private InfoWrapper infoWrapper;

    @PostMapping(path = "/course", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> register(
            @RequestBody Course course) {

        ServiceSegment info = contentService.createCourse(course);
        return infoWrapper.wrap(info, String.class);
    }

    @GetMapping(path = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> course(
            @RequestParam("course") String courseId) {

        ServiceSegment info = contentService.getCourseDetails(courseId);
        return infoWrapper.wrap(info, Course.class);
    }

    @PostMapping(path = "/edit/course", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> deleteCourse(
            @RequestParam("course") String courseId) {

        ServiceSegment info = contentService.deleteCourse(courseId);
        return infoWrapper.wrap(info, Course.class);
    }

    @GetMapping(path = "/course/course-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course[]> courseList(
            @RequestParam("user") String userId) {

        ServiceSegment info = contentService.getCourseListByUser(userId);
        return infoWrapper.wrap(info, Course[].class);
    }

    @PostMapping(path = "/file/upload", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> upload(
            @RequestBody CourseFile courseFile) {

        ServiceSegment info = contentService.uploadCourseFile(courseFile);
        return infoWrapper.wrap(info, String.class);
    }

    @GetMapping(path = "/file/download")
    public ResponseEntity<CourseFile> download(
            @RequestParam("file") String fileId) {

        ServiceSegment info = contentService.downloadCourseFile(fileId);
        return infoWrapper.wrap(info, CourseFile.class);
    }
}
