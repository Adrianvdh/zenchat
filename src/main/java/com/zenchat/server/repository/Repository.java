package com.zenchat.server.repository;

import java.util.List;

public interface Repository<T> {
    T save(T user);

    List<T> findAll();

    void deleteAll();

}
