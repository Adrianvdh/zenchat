package com.zenchat.server.config;

import com.mongodb.client.MongoDatabase;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.api.user.repository.UserRepositoryImpl;
import com.zenchat.server.repository.RepositoryProvider;
import com.zenchat.server.repository.RepositoryRegistry;

public class RepositoriesConfiguration {

    public static RepositoryRegistry configureRepositories(MongoDatabase mongoDatabase) {
        return RepositoryRegistry.fromProviders(
                new RepositoryProvider<>(UserRepository.class, new UserRepositoryImpl(mongoDatabase.getCollection("users", User.class)))
        );
    }
}
