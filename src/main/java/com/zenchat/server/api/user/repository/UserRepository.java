package com.zenchat.server.api.user.repository;

import com.zenchat.server.api.user.model.Session;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.repository.Repository;

public interface UserRepository extends Repository<User> {
    User findByUsername(String username);

    void updateUserSession(String userId, Session session);
}
