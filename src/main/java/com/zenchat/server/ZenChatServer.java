package com.zenchat.server;

import com.zenchat.server.config.ServerProperties;
import com.zenchat.server.config.ServerPropertiesLoader;
import com.zenchat.server.network.SocketServer;
import lombok.extern.slf4j.Slf4j;

import static com.zenchat.server.repository.RepositoryConfigurer.setupDatabase;

@Slf4j
public class ZenChatServer {
    private ServerProperties serverProperties;
    private SocketServer server;

    public ZenChatServer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public void startup() {
        log.info("ZenChat server is starting up...");

        setupDatabase(serverProperties);
        server = new SocketServer(serverProperties.getPort());
        server.start();
    }

    public void shutdown() {
        log.info("ZenChat server is shutting down...");

        if (server != null) {
            server.stop();
        }
    }

    public static void main(String[] args) {
        ServerProperties serverProperties = ServerPropertiesLoader.fromProperties("application.properties");

        ZenChatServer zenChatServer = new ZenChatServer(serverProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(zenChatServer::shutdown));
        zenChatServer.startup();
    }

}
