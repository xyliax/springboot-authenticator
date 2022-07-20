package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "USERS")
@EnableMongoAuditing
@CompoundIndexes({@CompoundIndex(name = "username_password_idx",
        def = "{'username': 1, 'password': 1}", unique = true)})
public class User {
    //@MongoId(value=FieldType.STRING)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String publicKy;
    private String password;
    private String EncryptD;
}
