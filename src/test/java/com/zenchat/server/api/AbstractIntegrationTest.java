package com.zenchat.server.api;

import com.zenchat.server.ZenChatServer;
import com.zenchat.server.config.ServerProperties;
import com.zenchat.server.config.ServerPropertiesLoader;
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
    private static ZenChatServer server;
    private static MongodProcess mongod;

    protected static int PORT;
    protected static String HOST;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ServerProperties serverProperties = ServerPropertiesLoader.fromProperties("application.properties");

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.V3_4_15)
                .net(new Net(27018, false))
                .timeout(new Timeout(60000))
                .build();

        mongod = MongodStarter.getDefaultInstance()
                .prepare(mongodConfig)
                .start();

        server = new ZenChatServer(serverProperties);
        server.startup();

        HOST = "localhost";
        PORT = serverProperties.getPort();
    }

    @AfterClass
    public static void breakDown() {
        server.shutdown();

        mongod.stop();
    }

}
