package com.zenchat.server.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class Repositories {
    private static Repositories INSTANCE;

    private Map<Class, Repository> repositories = new HashMap<>();

    protected Repositories() {
    }

    public static <T extends Repository> T getRepository(Class<T> repository) {
        if (!getInstance().repositories.containsKey(repository)) {
            throw new RepositoryException(String.format("Repository '%s' could not be found. Please register this repository first!", repository.getSimpleName()));
        }
        return (T) getInstance().repositories.get(repository);
    }

    public <T extends Repository> void registerOne(Class<? extends Repository> repository, T repositoryImpl) {
        this.repositories.put(repository, repositoryImpl);

        log.info("Registered repository '{}'", repositoryImpl.getClass().getSimpleName());
    }

    protected static void constructSingleton(Repositories repositories) {
        INSTANCE = repositories;
    }

    private static Repositories getInstance() {
        return INSTANCE;
    }
}
