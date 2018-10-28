package com.zenchat.server.api.registration.repository;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User findByUsername(String username);

    List<User> findAll();

    void deleteAll();

    boolean exists(String username);
}
