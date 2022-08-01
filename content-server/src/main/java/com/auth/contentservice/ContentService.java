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

@Service
public class ContentService {
    @Resource
    private MongoRepository mongoRepository;
    @Resource
    private LocalFileRepository localFileRepository;

    public ServiceSegment createCourse(Course course) {
        try {
            Course newCourse = mongoRepository.createCourse(course);
            return new ServiceSegment(newCourse.getCourseId());
        } catch (DuplicateKeyException duplicateKeyException) {
            return new ServiceSegment(Cause.DUP_NAME);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment uploadCourseFile(CourseFile courseFile) {
        try {
            localFileRepository.createCourseFile(courseFile);
            String filePath = mongoRepository.createCourseFile(courseFile);
            if (filePath == null)
                return new ServiceSegment(Cause.UNDEF_ARG);
            return new ServiceSegment(filePath);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment downloadCourseFile(String fileId) {
        try {
            CourseFile courseFile = localFileRepository.readFileByFileId(fileId);
            if (courseFile == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(courseFile);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
