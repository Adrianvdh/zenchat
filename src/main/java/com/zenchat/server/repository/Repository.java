package com.zenchat.server.repository;

public interface Repository<T> {
    void save(T user);

    void deleteAll();

}
