package com.zenchat.server.repository;

import com.mongodb.client.MongoDatabase;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.api.user.repository.UserRepositoryImpl;

public class RepositoryConfigurer {
    public static void setupRepositories(MongoDatabase mongoDatabase) {
        Repositories repositories = new Repositories();
        repositories.registerOne(UserRepository.class, new UserRepositoryImpl(mongoDatabase.getCollection("users", User.class)));

        Repositories.constructSingleton(repositories);
    }
}
