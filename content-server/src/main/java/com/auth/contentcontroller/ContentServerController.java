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
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentServerController {
    @Resource
    private ContentService contentService;
    @Resource
    private InfoWrapper infoWrapper;

    @PostMapping(path = "/course")
    public ResponseEntity<Course> register(
            @RequestBody Course course) {

        ServiceSegment info = contentService.createCourse(course);
        return infoWrapper.wrap(info, Course.class);
    }

    @GetMapping(path = "/course")
    public ResponseEntity<Course> course(
            @RequestParam("course") String courseId) {

        ServiceSegment info = contentService.getCourseDetails(courseId);
        return infoWrapper.wrap(info, Course.class);
    }

    @PostMapping(path = "/edit/course")
    public ResponseEntity<Course> deleteCourse(
            @RequestParam("course") String courseId) {

        ServiceSegment info = contentService.deleteCourse(courseId);
        return infoWrapper.wrap(info, Course.class);
    }

    @GetMapping(path = "/course/course-list")
    public ResponseEntity<Course[]> courseList(
            @RequestParam("user") String userId) {

        ServiceSegment info = contentService.getCourseListByUser(userId);
        return infoWrapper.wrap(info, Course[].class);
    }

    @PostMapping(path = "/file/upload")
    public ResponseEntity<CourseFile> upload(
            @RequestBody CourseFile courseFile) {

        ServiceSegment info = contentService.uploadCourseFile(courseFile);
        return infoWrapper.wrap(info, CourseFile.class);
    }

    @PostMapping(path = "/file/delete")
    public ResponseEntity<CourseFile> delete(
            @RequestParam("course") String courseId,
            @RequestParam("file") String fileId) {

        ServiceSegment info = contentService.deleteCourseFile(fileId, courseId);
        return infoWrapper.wrap(info, CourseFile.class);
    }
}
