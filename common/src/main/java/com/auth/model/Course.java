package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Document(collection = "COURSES")
public class Course {
    @MongoId(targetType = FieldType.STRING)
    private String courseId;
    @Indexed
    private String courseName;
    private Boolean valid;
    private ArrayList<String> courseFileIds;

    public static boolean isValid(Object o) {
        return o instanceof Course && ((Course) o).valid;
    }
}
