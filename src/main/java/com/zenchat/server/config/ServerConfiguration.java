package com.zenchat.server.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.zenchat.server.config.ConfigurationProperties.SERVER_PORT;
import static com.zenchat.server.config.ConfigurationProperties.SERVER_PORT_DEFAULT;

@Data
public class ServerConfiguration {
    private int port = SERVER_PORT_DEFAULT;

    public static ServerConfiguration fromProperties(String fileName) {
        InputStream resourceAsStream = ServerConfiguration.class.getClassLoader().getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerConfiguration serverConfiguration = new ServerConfiguration();
        if(properties.containsKey(SERVER_PORT)) {
            serverConfiguration.setPort(Integer.valueOf(properties.getProperty(SERVER_PORT)));
        }

        return serverConfiguration;
    }
}
