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
@Document(collection = "ARCHIVES")
public class Archive {
    @MongoId(targetType = FieldType.STRING)
    private String archiveId;
    @Indexed
    private String archiveName;
    private String appendix;
    private String description;
    private String coverImage;
    private String parentId;
    private ArrayList<Archive> subArchives;
    private ArrayList<Course> courses;

    public void addArchive(Archive archive) {
        for (Archive subArchive : subArchives) {
            if (subArchive.getArchiveId().equals(archive.getArchiveId()))
                return;
        }
        subArchives.add(archive);
    }

    public void delArchive(Archive archiveDel) {
        for (Archive archive : subArchives) {
            if (archive.getArchiveId().equals(archiveDel.getArchiveId())) {
                subArchives.remove(archive);
                return;
            }
        }
    }

    public void addCourse(Course course) {
        for (Course subCourse : courses) {
            if (subCourse.getCourseId().equals(course.getCourseId()))
                return;
        }
        courses.add(course);
    }

    public void delCourse(Course courseDel) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseDel.getCourseId())) {
                courses.remove(course);
                return;
            }
        }
    }
}
