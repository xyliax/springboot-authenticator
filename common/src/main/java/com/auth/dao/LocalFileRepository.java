package com.auth.dao;

import com.auth.model.CourseFile;
import lombok.SneakyThrows;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.auth.config.FileMappingConfig.FILE_MAPPING_PATH_PREFIX;
import static com.auth.config.FileMappingConfig.FILE_REAL_PATH;

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
        File fileRealPath = new File(FILE_REAL_PATH);
        if (!fileRealPath.exists())
            if (!fileRealPath.mkdirs())
                return null;
        String fileName = multipartFile.getOriginalFilename();
        String newFileName = getTime() + "_" + fileName;
        File file = new File(fileRealPath, newFileName);
        multipartFile.transferTo(file);
        String fileId = objectIdGenerator.generate().toString();
        CourseFile courseFile = new CourseFile(fileId, courseId, fileName, description,
                FILE_MAPPING_PATH_PREFIX + newFileName, FILE_REAL_PATH + newFileName);
        courseFile.setFileId(fileId);
        return courseFile;
    }

    public void deleteCourseFile(CourseFile courseFile) {
        File file = new File(courseFile.getLocation());
        if (!file.delete())
            file.deleteOnExit();
    }
}
