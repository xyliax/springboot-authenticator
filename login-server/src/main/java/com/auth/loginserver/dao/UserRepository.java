package com.auth.loginserver.dao;

import com.auth.model.User;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserRepository {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private ObjectIdGenerator objectIdGenerator;

    public User createUser(User user) {
        user.setUserId(objectIdGenerator.generate().toString());
        return mongoTemplate.insert(user);
    }

    public User readUser(User user) {
        Query query = new Query(Criteria.
                where("username").is(user.getUsername()));
        List<User> list = mongoTemplate.find(query, User.class);
        return list.get(0);
    }
}
