package com.zenchat.server;

import com.zenchat.server.config.ServerConfiguration;
import com.zenchat.server.repository.EmbeddedDatabaseBuilder;
import com.zenchat.server.repository.HsqldbConnection;

import java.sql.Connection;

public class ZenChatTestServer extends ZenChatServer {

    public ZenChatTestServer(ServerConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected Connection getDbConnection() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.configureConnection(HsqldbConnection.getInstance().getInprocessConnection());
        builder.addUpdateScript("hsqldb/create-schema.sql");

        return builder.build();
    }
}
