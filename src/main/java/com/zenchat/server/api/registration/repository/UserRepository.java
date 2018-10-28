package com.zenchat.server.api.registration.repository;

import com.zenchat.server.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository {

    User save(User user);

    User findByUsername(String username);

    List<User> findAll();

    void deleteAll();

    boolean exists(String username);
}
