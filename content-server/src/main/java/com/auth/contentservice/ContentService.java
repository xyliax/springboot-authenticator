package com.auth.contentservice;

import com.auth.dao.LocalFileRepository;
import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.util.ServiceSegment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContentService {
    @Resource
    private MongoRepository mongoRepository;
    @Resource
    private LocalFileRepository localFileRepository;

    public ServiceSegment createCourse(Course course) {
        try {
            Course newCourse = mongoRepository.createCourse(course);
            return new ServiceSegment(newCourse);
        } catch (DuplicateKeyException duplicateKeyException) {
            return new ServiceSegment(Cause.DUP_NAME);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getCourseDetails(String courseId) {
        try {
            Course course = mongoRepository.readCourseById(courseId);
            if (course == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(course);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment deleteCourse(String courseId) {
        try {
            Course course = mongoRepository.deleteCourseById(courseId);
            if (course == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(course);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getCourseListByUser(String userId) {
        try {
            List<Course> courseList;
            if ("*".equals(userId))
                courseList = mongoRepository.readCourseAll();
            else courseList = mongoRepository.readCourseByUser(userId);
            if (courseList == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(courseList.toArray(Course[]::new));
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment uploadCourseFile(CourseFile courseFile) {
        try {
            CourseFile courseFileSaved = mongoRepository.createCourseFile(courseFile);
            if (courseFileSaved == null)
                return new ServiceSegment(Cause.UNDEF_ARG);
            return new ServiceSegment(courseFileSaved);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment deleteCourseFile(String fileId, String courseId) {
        try {
            CourseFile courseFile = mongoRepository.deleteCourseFile(fileId, courseId);
            if (courseFile == null)
                return new ServiceSegment(Cause.UNDEF_ARG);
            localFileRepository.deleteCourseFile(courseFile);
            return new ServiceSegment(courseFile);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
