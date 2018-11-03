package com.zenchat.server.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepositoryProvider<R extends Repository, I extends R> {
    private Class<R> repository;
    private I repositoryImpl;
}
