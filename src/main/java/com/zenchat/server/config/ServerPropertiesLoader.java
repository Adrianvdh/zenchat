package com.zenchat.server.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.zenchat.server.config.ConfigurationProperties.*;

@Data
public class ServerPropertiesLoader {

    public static ServerProperties fromProperties(String fileName) {
        InputStream resourceAsStream = ServerPropertiesLoader.class.getClassLoader().getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ServerProperties serverConfiguration = new ServerProperties();
        if(properties.containsKey(SERVER_PORT)) {
            serverConfiguration.setPort(Integer.valueOf(properties.getProperty(SERVER_PORT)));
        }

        ServerProperties.DatabaseConfiguration databaseConfiguration = new ServerProperties.DatabaseConfiguration();
        if(properties.containsKey(DATABASE_SERVER_HOST)) {
            databaseConfiguration.setHost(properties.getProperty(DATABASE_SERVER_HOST));
        }

        if(properties.containsKey(DATABASE_SERVER_NAME)) {
            databaseConfiguration.setName(properties.getProperty(DATABASE_SERVER_NAME));
        }

        if(properties.containsKey(DATABASE_SERVER_PORT)) {
            databaseConfiguration.setPort(Integer.valueOf(properties.getProperty(DATABASE_SERVER_PORT)));
        }

        serverConfiguration.setDatabaseConfiguration(databaseConfiguration);
        return serverConfiguration;
    }
}
