package com.zenchat.server;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.zenchat.server.config.ServerProperties;
import com.zenchat.server.config.ServerPropertiesLoader;
import com.zenchat.server.message.MessageHandlersConfigurer;
import com.zenchat.server.network.SocketServer;
import com.zenchat.server.repository.RepositoryConfigurer;
import com.zenchat.server.repository.mongo.codec.JodaDateTimeCodec;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.*;

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

        String mongoConnectionString = String.format("mongodb://%s:%s",
                serverProperties.getDatabaseConfiguration().getHost(),
                serverProperties.getDatabaseConfiguration().getPort());


        CodecRegistry codecRegistry = fromRegistries(
                fromCodecs(new JodaDateTimeCodec()),
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()
                )
        );


        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .applyConnectionString(new ConnectionString(mongoConnectionString))
                .build();

        mongoClient = MongoClients.create(mongoClientSettings);
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
