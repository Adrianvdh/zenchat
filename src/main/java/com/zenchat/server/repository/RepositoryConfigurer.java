package com.zenchat.server.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.api.user.repository.UserRepositoryImpl;
import com.zenchat.server.config.ServerProperties;

public class RepositoryConfigurer {
    public static void setupDatabase(ServerProperties serverProperties) {
        MongoClient mongoClient = MongoClients.create(String.format("mongodb://%s:%s",
                serverProperties.getDatabaseConfiguration().getHost(),
                serverProperties.getDatabaseConfiguration().getPort())
        );
        MongoDatabase mongoDatabase = mongoClient.getDatabase(serverProperties.getDatabaseConfiguration().getName());

        Repositories repositories = new Repositories();
        repositories.registerOne(UserRepository.class, new UserRepositoryImpl(mongoDatabase.getCollection("users")));

        Repositories.constructSingleton(repositories);
    }
}
