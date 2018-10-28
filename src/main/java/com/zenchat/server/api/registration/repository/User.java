package com.zenchat.server.api.registration.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;

    public User(String username) {
        this.username = username;
    }
}
