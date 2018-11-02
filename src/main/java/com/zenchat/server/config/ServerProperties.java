package com.zenchat.server.config;

import lombok.Data;

@Data
public class ServerProperties {
    private int port;
    private DatabaseConfiguration databaseConfiguration;

    @Data
    public static class DatabaseConfiguration {
        private String host;
        private int port;
        private String name;
    }

}
