package com.auth.contentservice;

import com.auth.dao.LocalFileRepository;
import com.auth.dao.MongoRepository;
import com.auth.defenum.Cause;
import com.auth.model.Archive;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.util.ServiceSegment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentService {
    @Resource
    private MongoRepository mongoRepository;
    @Resource
    private LocalFileRepository localFileRepository;

    public ServiceSegment createCourse(Course course) {
        try {
            Course newCourse = mongoRepository.createCourse(course);
            if (newCourse == null)
                return new ServiceSegment(Cause.NO_RESULT);
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
            localFileRepository.deleteCourse(course);
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

    public ServiceSegment createArchive(Archive archive) {
        try {
            Archive archiveSaved = mongoRepository.createArchive(archive);
            if (archiveSaved == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(archiveSaved);
        } catch (DuplicateKeyException duplicateKeyException) {
            return new ServiceSegment(Cause.DUP_NAME);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment getArchiveDetails(String archiveId) {
        try {
            Archive archive;
            if (archiveId.isEmpty()) {
                archive = new Archive(null, "DEFAULT",
                        "default archive", null, null, null,
                        (ArrayList<Archive>) mongoRepository.readArchiveByParent(""),
                        (ArrayList<Course>) mongoRepository.readCourseByParent(""));
            } else {
                archive = mongoRepository.readArchiveById(archiveId);
                if (archive == null)
                    return new ServiceSegment(Cause.NO_RESULT);
            }
            return new ServiceSegment(archive);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment deleteArchive(boolean delete, String archiveId) {
        try {
            Archive archive;
            if (delete)
                archive = mongoRepository.deleteArchiveById(archiveId);
            else
                archive = mongoRepository.dismissArchiveById(archiveId);
            if (archive == null)
                return new ServiceSegment(Cause.NO_RESULT);
            return new ServiceSegment(archive);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }

    public ServiceSegment archiveCourse(String archiveId, Map<String, String>[] idMapArray) {
        try {
            Archive archive = mongoRepository.readArchiveById(archiveId);
            if (archive == null)
                return new ServiceSegment(Cause.NO_RESULT);
            for (Map<String, String> idMap : idMapArray) {
                String courseId = idMap.get("courseId");
                String actionStr = idMap.get("action");
                boolean action;
                if ("true".equalsIgnoreCase(actionStr))
                    action = true;
                else if ("false".equalsIgnoreCase(actionStr))
                    action = false;
                else continue;
                Course course = mongoRepository.readCourseById(courseId);
                if (course == null)
                    continue;
                if (action) {
                    if (!course.getParentId().isEmpty()) {
                        Archive parentArchive = mongoRepository.readArchiveById(course.getParentId());
                        if (parentArchive != null) {
                            parentArchive.delCourse(course);
                            mongoRepository.updateArchive(parentArchive);
                        }
                    }
                    course.setParentId(archiveId);
                    archive.addCourse(course);
                } else {
                    if (!course.getParentId().isEmpty()) {
                        Archive parentArchive = mongoRepository.readArchiveById(course.getParentId());
                        if (parentArchive != null) {
                            parentArchive.delCourse(course);
                            mongoRepository.updateArchive(parentArchive);
                        }
                    }
                    course.setParentId("");
                }
                mongoRepository.updateCourse(course);
            }
            mongoRepository.updateArchive(archive);
            return new ServiceSegment(archive);
        } catch (RuntimeException runtimeException) {
            return new ServiceSegment(Cause.UNKNOWN);
        }
    }
}
