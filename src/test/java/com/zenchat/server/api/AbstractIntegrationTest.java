package com.zenchat.server.api;

import com.zenchat.server.ZenChatServer;
import com.zenchat.server.config.ServerProperties;
import com.zenchat.server.config.ServerPropertiesLoader;
import com.zenchat.server.repository.RepositoryConfigurer;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Timeout;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractIntegrationTest {
    protected static ServerProperties serverProperties;
    protected static ZenChatServer server;

    private static MongodProcess mongodProcess;

    protected static int PORT;
    protected static String HOST;

    @BeforeClass
    public static void beforeClass() throws Exception {
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27018, false))
                .build();

        mongodProcess = MongodStarter.getDefaultInstance()
                .prepare(mongodConfig)
                .start();

        serverProperties = ServerPropertiesLoader.fromProperties("application.properties");

        RepositoryConfigurer.setupDatabase(serverProperties);

        server = new ZenChatServer(serverProperties);
        server.startup();

        PORT = serverProperties.getPort();
    }

    @AfterClass
    public static void breakDown() {
        server.shutdown();
        mongodProcess.stop();
    }

}
