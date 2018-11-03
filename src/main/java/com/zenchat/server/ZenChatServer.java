package com.zenchat.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.zenchat.server.config.MongoConfiguration;
import com.zenchat.server.config.ServerProperties;
import com.zenchat.server.config.ServerPropertiesLoader;
import com.zenchat.server.message.MessageHandlersConfigurer;
import com.zenchat.server.network.SocketServer;
import com.zenchat.server.repository.RepositoryConfigurer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZenChatServer {
    private ServerProperties serverProperties;
    private SocketServer server;
    private MongoClient mongoClient;

    public ZenChatServer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public void startup() {
        log.info("ZenChat server is starting up...");

        mongoClient = MongoConfiguration.configureMongo(serverProperties);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(serverProperties.getDatabaseConfiguration().getName());

        RepositoryConfigurer.setupRepositories(mongoDatabase);
        MessageHandlersConfigurer.setupMessageHandlers();

        server = new SocketServer(serverProperties.getPort());
        server.start();
    }

    public void shutdown() {
        log.info("ZenChat server is shutting down...");

        if (server != null) {
            server.stop();
        }

        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
        ServerProperties serverProperties = ServerPropertiesLoader.fromProperties("application.properties");

        ZenChatServer zenChatServer = new ZenChatServer(serverProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(zenChatServer::shutdown));
        zenChatServer.startup();
    }

}
