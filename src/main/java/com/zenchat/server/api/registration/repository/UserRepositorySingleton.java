package com.zenchat.server.api.registration.repository;


import com.zenchat.server.repository.HsqldbConnection;

public class UserRepositorySingleton {
    private static UserRepositorySingleton holder = new UserRepositorySingleton();
    private UserRepository userRepository;

    private UserRepositorySingleton() {
    }

    public static UserRepositorySingleton getInstance() {
        return holder;
    }

    public UserRepository get() {
        if (userRepository == null) {
            userRepository = new SqlUserRepository(HsqldbConnection.getInstance().getInprocessConnection());
        }
        return userRepository;
    }
}
