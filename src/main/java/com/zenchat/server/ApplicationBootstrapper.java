package com.zenchat.server;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.registration.UserRegistrationHandler;
import com.zenchat.server.api.registration.repository.SqlUserRepository;
import com.zenchat.server.api.registration.repository.UserRepository;
import com.zenchat.server.listener.Server;
import com.zenchat.server.repository.HsqldbConnection;
import com.zenchat.server.repository.Repository;
import com.zenchat.server.requesthandler.RequestHandler;
import com.zenchat.server.requesthandler.RequestHandlerException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApplicationBootstrapper {

    private static Map<Class, Repository> repositories = new HashMap<>();
    private static Map<Class, RequestHandler> handlers = new HashMap<>();

    public static Repository getRespository(Class repository) {
        return repositories.get(repository);
    }

    public static RequestHandler getHandler(Class requestClass) {
        if(requestClass == null || !handlers.containsKey(requestClass)) {
            String message = String.format("Request requestClass could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new RequestHandlerException(message);
        }
        return handlers.get(requestClass);
    }

    private static void registerRepositories() {
        repositories.put(UserRepository.class, new SqlUserRepository(HsqldbConnection.getInstance().getInprocessConnection()));
    }

    private static void registerRequestHandlers() {
        handlers.put(RegisterUserRequest.class, new UserRegistrationHandler());
    }

    public static void loadContext() {
        registerRepositories();
        registerRequestHandlers();
    }

    public static void main(String[] args) {
        loadContext();

        Server server = new Server(31145);
        server.start();
    }

}
