package com.auth.loginserver.service;

import com.auth.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private MongoTemplate mongoTemplate;

    public String userRegister(User user) {
        mongoTemplate.save(user);
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        List<User> list = mongoTemplate.find(query, User.class);
        return list.get(0).toString() + "???" + list.get(0).getUserId();
    }
}
