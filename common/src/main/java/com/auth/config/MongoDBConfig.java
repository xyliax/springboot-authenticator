package com.auth.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.bson.codecs.ObjectIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.database}")
    String dbName;

    @Bean
    ObjectIdGenerator objectIdGenerator() {
        return new ObjectIdGenerator();
    }

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return GridFSBuckets.create(database);
    }
}
