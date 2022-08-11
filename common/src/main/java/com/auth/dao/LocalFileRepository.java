package com.auth.dao;

import com.auth.model.Course;
import com.auth.model.CourseFile;
import lombok.SneakyThrows;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.auth.config.FileMappingConfig.fileMappingPathPrefix;
import static com.auth.config.FileMappingConfig.fileRealPath;

@Repository
public class LocalFileRepository {
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    private static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    //Course File
    @SneakyThrows
    public CourseFile createCourseFile(MultipartFile multipartFile, String courseId, String description) {
        File fileDir = new File(fileRealPath + courseId + File.separator);
        if (!fileDir.exists())
            if (!fileDir.mkdirs())
                return null;
        String fileName = multipartFile.getOriginalFilename();
        String newFileName = getTime() + "_" + fileName;
        File file = new File(fileDir, newFileName);
        multipartFile.transferTo(file);
        String fileId = objectIdGenerator.generate().toString();
        Date date = new Date();
        CourseFile courseFile = new CourseFile(fileId, courseId, fileName, description,
                String.format("%tY年%tm月%td日 %tH:%tM:%tS", date, date, date, date, date, date),
                fileMappingPathPrefix + courseId + File.separator + newFileName,
                fileRealPath + courseId + File.separator + newFileName);
        courseFile.setFileId(fileId);
        return courseFile;
    }

    public CourseFile createCourseFileNextCloud(CourseFile courseFile) {
        String fileId = objectIdGenerator.generate().toString();
        courseFile.setFileId(fileId);
        Date date = new Date();
        courseFile.setCreateDate(String.format
                ("%tY年%tm月%td日 %tH:%tM:%tS", date, date, date, date, date, date));
        return courseFile;
    }

    public void deleteCourseFile(CourseFile courseFile) {
        File file = new File(courseFile.getLocation());
        if (!file.delete())
            file.deleteOnExit();
    }

    public void deleteCourse(Course course) {
        File dir = new File(fileRealPath + course.getCourseId() + File.separator);
        if (!dir.delete()) {
            File[] files = dir.listFiles();
            if (files == null)
                return;
            for (File file : files) {
                if (!file.delete())
                    file.deleteOnExit();
            }
            if (!dir.delete())
                dir.deleteOnExit();
        }
    }
}
