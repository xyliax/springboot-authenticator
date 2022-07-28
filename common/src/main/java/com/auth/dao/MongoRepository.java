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
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MongoRepository {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private GridFsTemplate gridFsTemplate;
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    //USERS
    public User createUser(User user) {
        String userId = objectIdGenerator.generate().toString();
        user.setUserId(userId);
        return mongoTemplate.insert(user, "USERS");
    }

    public User readUserById(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class, "USERS");
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
        return mongoTemplate.insert(course, "COURSES");
    }

    public Course readCourseById(String courseId) {
        Query query = new Query(Criteria.where("courseId").is(courseId));
        return mongoTemplate.findOne(query, Course.class, "COURSES");
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

    //Course File
    public CourseFile createCourseFile(CourseFile courseFile) {
        String fileId = objectIdGenerator.generate().toString();
        courseFile.setFileId(fileId);
        return mongoTemplate.insert(courseFile, "FILES");
    }

    public CourseFile readFileByFileId(String fileId) {
        Query query = new Query(Criteria.where("fileId").is(fileId));
        return mongoTemplate.findOne(query, CourseFile.class, "FILES");
    }
}
