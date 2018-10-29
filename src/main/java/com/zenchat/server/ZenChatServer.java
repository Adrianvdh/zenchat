package com.zenchat.server;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.registration.UserRegistrationHandler;
import com.zenchat.server.api.registration.repository.SqlUserRepository;
import com.zenchat.server.api.registration.repository.UserRepository;
import com.zenchat.server.config.ServerConfiguration;
import com.zenchat.server.message.MessageHandler;
import com.zenchat.server.message.MessageHandlerException;
import com.zenchat.server.network.SocketServer;
import com.zenchat.server.repository.EmbeddedDatabaseBuilder;
import com.zenchat.server.repository.HsqldbConnection;
import com.zenchat.server.repository.Repository;
import com.zenchat.server.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ZenChatServer {
    private ServerConfiguration configuration;

    private SocketServer server;
    private static Connection dbConnection;
    private static Map<Class, Repository> repositories = new HashMap<>();
    private static Map<Class, MessageHandler> handlers = new HashMap<>();

    public ZenChatServer(ServerConfiguration configuration) {
        this.configuration = configuration;
    }

    public static <T extends Repository> T getRepository(Class<T> repository) {
        if (!repositories.containsKey(repository)) {
            throw new RepositoryException(String.format("Repository '%s' could not be found. Please register this repository!", repository.getSimpleName()));
        }
        return (T) repositories.get(repository);
    }

    public static MessageHandler getHandler(Class requestClass) {
        if (requestClass == null || !handlers.containsKey(requestClass)) {
            String message = String.format("Request requestClass could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new MessageHandlerException(message);
        }
        return handlers.get(requestClass);
    }

    private static void setDbConnection(Connection connection) {
        dbConnection = connection;
    }

    protected Connection getDbConnection() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.configureConnection(HsqldbConnection.getInstance().getInprocessConnection());
        builder.addUpdateScript("hsqldb/create-schema.sql");

        return builder.build();
    }

    private static void registerRepositories() {
        repositories.put(UserRepository.class, new SqlUserRepository(dbConnection));
    }

    private static void registerRequestHandlers() {
        handlers.put(RegisterUserRequest.class, new UserRegistrationHandler(getRepository(UserRepository.class)));
    }

    public void startup() {
        log.info("ZenChat server is starting up...");

        Connection dbConnection = getDbConnection();
        if (dbConnection == null) {
            log.error("Cannot connect to SQL server...");
            System.exit(1);
            return;
        }

        setDbConnection(dbConnection);

        registerRepositories();
        registerRequestHandlers();

        server = new SocketServer(configuration.getPort());
        server.start();
    }

    public void shutdown() {
        log.info("ZenChat server is shutting down...");

        if (server != null) {
            server.stop();
        }
    }

    public static void main(String[] args) {
        ServerConfiguration serverConfiguration = ServerConfiguration.fromProperties("application.properties");
        ZenChatServer zenChatServer = new ZenChatServer(serverConfiguration);

        Runtime.getRuntime().addShutdownHook(new Thread(zenChatServer::shutdown));

        zenChatServer.startup();
    }

}
