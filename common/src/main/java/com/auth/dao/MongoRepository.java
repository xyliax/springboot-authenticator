package com.auth.dao;

import com.auth.defenum.Role;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.model.User;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoRepository {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    //USERS
    public User createUser(User user) {
        String userId = objectIdGenerator.generate().toString();
        user.setUserId(userId);
        return mongoTemplate.insert(user, "USERS");
    }

    public User readUserById(String userId) {
        return mongoTemplate.findById(userId, User.class, "USERS");
    }

    public User readUserByName(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class, "USERS");
    }

    public User updateUser(User user) {
        return mongoTemplate.save(user, "USERS");
    }

    public List<User> readUserAll() {
        return mongoTemplate.findAll(User.class, "USERS");
    }

    public List<User> readUserByRole(Role role) {
        Query query = new Query(Criteria.where("userRole").is(role));
        return mongoTemplate.find(query, User.class, "USERS");
    }

    //Course
    public Course createCourse(Course course) {
        String courseId = objectIdGenerator.generate().toString();
        course.setCourseId(courseId);
        course.setValid(true);
        course.setCourseFile(new ArrayList<>());
        return mongoTemplate.insert(course, "COURSES");
    }

    public Course readCourseById(String courseId) {
        return mongoTemplate.findById(courseId, Course.class, "COURSES");
    }

    public List<Course> readCourseByName(String courseName) {
        Query query = new Query(Criteria.where("courseName").is(courseName));
        return mongoTemplate.find(query, Course.class, "COURSES");
    }

    public boolean updateCourseForFile(String courseId, List<String> courseFileIds) {
        Query query = Query.query(Criteria.where("courseId").is(courseId));
        Update update = Update.update("courseFileIds", courseFileIds);
        return mongoTemplate.updateFirst(query, update, Course.class,
                "COURSES").wasAcknowledged();
    }

    public String createCourseFile(CourseFile courseFile) {
        Course course = readCourseById(courseFile.getCourseId());
        if (course == null)
            return null;
        course.addFile(courseFile.getFileId(), courseFile.getFileName(),
                courseFile.getDescription(), courseFile.getPath());
        mongoTemplate.save(course, "COURSES");
        return courseFile.getPath();
    }
}
