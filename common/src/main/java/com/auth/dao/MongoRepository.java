package com.auth.dao;

import com.auth.defenum.Role;
import com.auth.model.Archive;
import com.auth.model.Course;
import com.auth.model.CourseFile;
import com.auth.model.User;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

@Repository
public class MongoRepository {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    private static final String USER = "USERS";
    private static final String COURSE = "COURSES";
    private static final String ARCHIVE = "ARCHIVES";

    //USERS
    public User createUser(User user) {
        String userId = objectIdGenerator.generate().toString();
        user.setUserId(userId);
        user.setPermissions(new HashMap<>());
        return mongoTemplate.insert(user, USER);
    }

    public User readUserById(String userId) {
        return mongoTemplate.findById(userId, User.class, USER);
    }

    public User readUserByName(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class, USER);
    }

    public User updateUser(User user) {
        User userById = readUserById(user.getUserId());
        if (userById == null)
            return null;
        User userByName = readUserByName(user.getUsername());
        if (!Objects.equals(userById.getUserId(), userByName.getUserId()))
            return null;
        return mongoTemplate.save(user, USER);
    }

    public User deleteUserById(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        return mongoTemplate.findAndRemove(query, User.class, USER);
    }

    public List<User> readUserAll() {
        return mongoTemplate.findAll(User.class, USER);
    }

    public List<User> readUserByRole(Role role) {
        Query query = new Query(Criteria.where("userRole").is(role));
        return mongoTemplate.find(query, User.class, USER);
    }

    //Course
    public Course createCourse(Course course) {
        String courseId = objectIdGenerator.generate().toString();
        course.setCourseId(courseId);
        course.setCourseFiles(new HashMap<>());
        return mongoTemplate.insert(course, COURSE);
    }

    public Course readCourseById(String courseId) {
        return mongoTemplate.findById(courseId, Course.class, COURSE);
    }

    public Course deleteCourseById(String courseId) {
        Query query = new Query(Criteria.where("_id").is(courseId));
        return mongoTemplate.findAndRemove(query, Course.class, COURSE);
    }

    public List<Course> readCourseByName(String courseName) {
        Query query = new Query(Criteria.where("courseName").is(courseName));
        return mongoTemplate.find(query, Course.class, COURSE);
    }

    public List<Course> readCourseAll() {
        return mongoTemplate.findAll(Course.class, COURSE);
    }

    public List<Course> readCourseByUser(String userId) {
        User user = readUserById(userId);
        if (user == null)
            return null;
        List<Course> courseList = new ArrayList<>();
        HashMap<Role, HashSet<String>> permissions = user.getPermissions();
        for (Role role : Role.viewer()) {
            HashSet<String> courseSet = permissions.get(role);
            if (courseSet == null) continue;
            for (String courseId : courseSet) {
                Course course = readCourseById(courseId);
                if (course != null)
                    courseList.add(course);
            }
        }
        return courseList;
    }

    public List<Course> readCourseByParent(String parentId) {
        Query query = new Query(Criteria.where("parentId").is(parentId));
        return mongoTemplate.find(query, Course.class, COURSE);
    }

    public void updateCourse(Course course) {
        mongoTemplate.save(course, COURSE);
    }

    public CourseFile createCourseFile(CourseFile courseFile) {
        Course course = readCourseById(courseFile.getCourseId());
        if (course == null)
            return null;
        course.addFile(courseFile);
        mongoTemplate.save(course, COURSE);
        return courseFile;
    }


    public CourseFile deleteCourseFile(String fileId, String courseId) {
        Course course = readCourseById(courseId);
        if (course == null)
            return null;
        CourseFile courseFile = course.getCourseFiles().get(fileId);
        course.delFile(fileId);
        mongoTemplate.save(course, COURSE);
        return courseFile;
    }

    public Archive createArchive(Archive archive) {
        String parentId = archive.getParentId();
        Archive parentArchive = readArchiveById(parentId);
        if (!parentId.isEmpty() && parentArchive == null)
            return null;
        String archiveId = objectIdGenerator.generate().toString();
        archive.setArchiveId(archiveId);
        archive.setSubArchives(new ArrayList<>());
        archive.setCourses(new ArrayList<>());
        archive.setParentId(parentId);
        Archive archiveSaved = mongoTemplate.insert(archive, ARCHIVE);
        if (!parentId.isEmpty()) {
            parentArchive.addArchive(archive);
            updateArchive(parentArchive);
        }
        return archiveSaved;
    }

    public void updateArchive(Archive archive) {
        mongoTemplate.save(archive, ARCHIVE);
    }

    public Archive deleteArchiveById(String archiveId) {
        Archive archive = readArchiveById(archiveId);
        if (archive == null)
            return null;
        for (Archive subArchive : archive.getSubArchives())
            deleteArchiveById(subArchive.getArchiveId());
        for (Course course : archive.getCourses())
            deleteCourseById(course.getCourseId());
        Query query = new Query(Criteria.where("_id").is(archiveId));
        return mongoTemplate.findAndRemove(query, Archive.class, ARCHIVE);
    }

    public Archive dismissArchiveById(String archiveId) {
        Archive archive = readArchiveById(archiveId);
        if (archive == null)
            return null;
        for (Archive subArchive : archive.getSubArchives())
            subArchive.setParentId(null);
        for (Course course : archive.getCourses())
            course.setParentId(null);
        archive.setSubArchives(new ArrayList<>());
        archive.setCourses(new ArrayList<>());
        return mongoTemplate.save(archive, ARCHIVE);
    }

    public Archive readArchiveById(String archiveId) {
        return mongoTemplate.findById(archiveId, Archive.class, ARCHIVE);
    }

    public List<Archive> readArchiveByParent(String parentId) {
        Query query = new Query(Criteria.where("parentId").is(parentId));
        return mongoTemplate.find(query, Archive.class, ARCHIVE);
    }
}
