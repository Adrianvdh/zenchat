package com.zenchat.server.api.user.model;

import com.zenchat.server.security.SecurityRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @BsonProperty("_id")
    private String userId;
    private String username;
    private String password;
    private SecurityRole securityRole;
    private Session session;

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}
