package com.zenchat.server.repository;

import java.util.Arrays;
import java.util.List;

public class RepositoryRegistry {

    private List<RepositoryProvider> repositoryProviders;

    private RepositoryRegistry(List<RepositoryProvider> repositoryProviders) {
        this.repositoryProviders = repositoryProviders;
    }

    public static RepositoryRegistry fromProviders(RepositoryProvider... repositoryProviders) {
        return new RepositoryRegistry(Arrays.asList(repositoryProviders));
    }

    protected List<RepositoryProvider> getRepositoryProviders() {
        return repositoryProviders;
    }
}

