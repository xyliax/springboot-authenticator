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
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
@Document(collection = "COURSES")
public class Course {
    @MongoId(targetType = FieldType.STRING)
    private String courseId;
    private static final String FILE_ID = "id";
    private static final String FILENAME = "name";
    private Boolean valid;
    private static final String FILE_DES = "description";
    private static final String PATH = "path";
    @Indexed(unique = true)
    private String courseName;
    private String description;
    private ArrayList<HashMap<String, String>> courseFile;

    public static boolean invalid(Object o) {
        return !(o instanceof Course) || !((Course) o).valid;
    }

    public void addFile(String fileId, String fileName, String description, String path) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(FILE_ID, fileId);
        hashMap.put(FILENAME, fileName);
        hashMap.put(FILE_DES, description);
        hashMap.put(PATH, path);
        courseFile.add(hashMap);
    }

    public void delFile(String fileId, String fileName, String description, String path) {
        courseFile.forEach(file -> {
            if (file.get(FILE_ID).equals(fileId) ||
                    file.get(FILENAME).equals(fileName) ||
                    file.get(FILE_DES).equals(description) ||
                    file.get(PATH).equals(path))
                courseFile.remove(file);
        });
    }
}
