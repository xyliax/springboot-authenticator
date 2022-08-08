package com.auth.contentcontroller;

import com.auth.contentservice.ContentService;
import com.auth.model.Archive;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.util.InfoWrapper;
import com.auth.util.ServiceSegment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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
    public ResponseEntity<CourseFile> uploadFile(
            @RequestBody CourseFile courseFile) {

        ServiceSegment info = contentService.uploadCourseFile(courseFile);
        return infoWrapper.wrap(info, CourseFile.class);
    }

    @PostMapping(path = "/file/delete")
    public ResponseEntity<CourseFile> deleteFile(
            @RequestParam("course") String courseId,
            @RequestParam("file") String fileId) {

        ServiceSegment info = contentService.deleteCourseFile(fileId, courseId);
        return infoWrapper.wrap(info, CourseFile.class);
    }

    @PostMapping(path = "/archive")
    public ResponseEntity<Archive> makeArchive(
            @RequestBody Archive archive) {

        ServiceSegment info = contentService.createArchive(archive);
        return infoWrapper.wrap(info, Archive.class);
    }

    @GetMapping(path = "/archive")
    public ResponseEntity<Archive> archive(
            @RequestParam(value = "archiveId", required = false) String archiveId) {

        ServiceSegment info = contentService.getArchiveDetails(archiveId);
        return infoWrapper.wrap(info, Archive.class);
    }

    @PostMapping(path = "/archive/delete")
    public ResponseEntity<Archive> deleteArchive(
            @RequestParam("delete") boolean delete,
            @RequestParam("archive") String archiveId) {

        ServiceSegment info = contentService.deleteArchive(delete, archiveId);
        return infoWrapper.wrap(info, Archive.class);
    }

    @PostMapping(path = "/archive/edit")
    public ResponseEntity<Archive> archiveCourse(
            @RequestParam("archive") String archiveId,
            @RequestBody Map<String, String>[] idMapArray) {

        ServiceSegment info = contentService.archiveCourse(archiveId, idMapArray);
        return infoWrapper.wrap(info, Archive.class);
    }
}
