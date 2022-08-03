package com.auth.model;

import com.auth.defenum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Document(collection = "COURSES")
public class Course {
    @MongoId(targetType = FieldType.STRING)
    private String courseId;
    @Indexed(unique = true)
    private String courseName;
    private String description;
    private HashMap<String, CourseFile> courseFiles;

    public void addFile(CourseFile courseFile) {
        courseFiles.put(courseFile.getFileId(), courseFile);
    }

    public void delFile(String fileId) {
        courseFiles.remove(fileId);
    }

    public boolean permit(User user, Role[] roles) {
        for (Role role : roles) {
            if (user.getUserRole() == role || user.hasPermission(role, courseId))
                return true;
        }
        return false;
    }
}
