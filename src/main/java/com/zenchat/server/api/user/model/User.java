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
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private SecurityRole securityRole;
    private Session session;

    public User(String userId, String firstName, String lastName, String username, String password, SecurityRole securityRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.securityRole = securityRole;
    }
}
