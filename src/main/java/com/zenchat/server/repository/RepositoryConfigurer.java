package com.zenchat.server.repository;

import com.mongodb.client.MongoDatabase;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.api.user.repository.UserRepositoryImpl;
import com.zenchat.server.config.RepositoriesConfiguration;

public class RepositoryConfigurer {
    public static void setupRepositories(MongoDatabase mongoDatabase) {
        Repositories repositories = new Repositories();

        RepositoryRegistry repositoryRegistry = RepositoriesConfiguration.configureRepositories(mongoDatabase);
        repositoryRegistry.getRepositoryProviders().forEach(repositoryProvider -> {
            repositories.registerOne(repositoryProvider.getRepository(), repositoryProvider.getRepositoryImpl());
        });

        Repositories.constructSingleton(repositories);
    }
}
