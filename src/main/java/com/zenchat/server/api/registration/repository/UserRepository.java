package com.zenchat.server.api.registration.repository;

import com.zenchat.server.repository.Repository;

public interface UserRepository extends Repository<User> {
    User findByUsername(String username);

    boolean exists(String username);
}
