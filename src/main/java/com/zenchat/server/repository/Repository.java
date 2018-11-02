package com.zenchat.server.repository;

import java.util.List;

public interface Repository<T> {
    void save(T user);

    List<T> findAll();

    void deleteAll();

}
