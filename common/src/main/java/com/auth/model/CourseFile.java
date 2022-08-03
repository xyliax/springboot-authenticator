package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseFile {
    private String fileId;
    private String courseId;
    private String fileName;
    private String fileDescription;
    private String path;
}
