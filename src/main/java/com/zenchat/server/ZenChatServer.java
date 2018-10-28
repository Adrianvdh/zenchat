package com.zenchat.server;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.registration.UserRegistrationHandler;
import com.zenchat.server.api.registration.repository.SqlUserRepository;
import com.zenchat.server.api.registration.repository.UserRepository;
import com.zenchat.server.network.SocketServer;
import com.zenchat.server.repository.HsqldbConnection;
import com.zenchat.server.repository.Repository;
import com.zenchat.server.message.MessageHandler;
import com.zenchat.server.message.MessageHandlerException;
import com.zenchat.server.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ZenChatServer {

    private static Connection dbConnection;
    private static Map<Class, Repository> repositories = new HashMap<>();
    private static Map<Class, MessageHandler> handlers = new HashMap<>();

    public static <T extends Repository> T getRepository(Class<T> repository) {
        if(!repositories.containsKey(repository)) {
            throw new RepositoryException(String.format("Repository '%s' could not be found. Please register this repository!", repository.getSimpleName()));
        }
        return (T) repositories.get(repository);
    }

    public static MessageHandler getHandler(Class requestClass) {
        if(requestClass == null || !handlers.containsKey(requestClass)) {
            String message = String.format("Request requestClass could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new MessageHandlerException(message);
        }
        return handlers.get(requestClass);
    }

    public static void setDbConnection(Connection connection) {
        dbConnection = connection;
    }

    private static void registerRepositories() {
        repositories.put(UserRepository.class, new SqlUserRepository(dbConnection));
    }

    private static void registerRequestHandlers() {
        handlers.put(RegisterUserRequest.class, new UserRegistrationHandler((UserRepository) getRepository(UserRepository.class)));
    }

    public static void loadContext() {
        registerRepositories();
        registerRequestHandlers();
    }

    public static void main(String[] args) {
        setDbConnection(HsqldbConnection.getInstance().getRemoteConnection());
        loadContext();

        SocketServer server = new SocketServer(31145);
        server.start();
    }

}
