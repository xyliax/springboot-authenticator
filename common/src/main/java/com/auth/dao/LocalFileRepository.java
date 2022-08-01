package com.auth.dao;

import com.auth.model.CourseFile;
import lombok.SneakyThrows;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class LocalFileRepository {
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    //Course File
    @SneakyThrows
    public void createCourseFile(CourseFile courseFile) {
        String fileId = objectIdGenerator.generate().toString();
        courseFile.setFileId(fileId);
    }

    public CourseFile readFileByFileId(String fileId) {
        return null;
    }
}
