package com.auth.contentservice;

import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.model.CourseFile;
import com.auth.model.ServiceSegment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ContentService {
    @Resource
    private MongoRepository mongoRepository;

    public ServiceSegment uploadCourseFile(String courseId, CourseFile courseFile) {
        try {
            String fileId = mongoRepository.createCourseFile(courseId, courseFile);
            if (fileId == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(fileId);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment downloadCourseFile(String fileId) {
        try {
            CourseFile courseFile = mongoRepository.readFileByFileId(fileId);
            if (courseFile == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(courseFile);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
