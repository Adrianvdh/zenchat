package com.zenchat.server.api.user.repository;

import com.zenchat.server.api.user.model.Session;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User> {
    User findByUsername(String username);

    void updateUserSession(String userId, Session session);

    User findBySessionId(String sessionId);

    List<User> findAll();
}
