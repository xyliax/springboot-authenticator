package com.auth.model;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableMongoAuditing
public class CourseFile {
    private String fileId;
    private GridFSFile file;
}
