package com.zenchat.server.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zenchat.server.repository.mongo.codec.JodaDateTimeCodec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class MongoConfiguration {

    public static MongoClient configureMongo(ServerProperties serverProperties) {
        String mongoConnectionString = String.format("mongodb://%s:%s", serverProperties.getDatabaseConfiguration().getHost(), serverProperties.getDatabaseConfiguration().getPort());

        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry codecRegistry = fromRegistries(fromCodecs(new JodaDateTimeCodec()), MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(10, TimeUnit.SECONDS))
                .codecRegistry(codecRegistry)
                .applyConnectionString(new ConnectionString(mongoConnectionString))
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
