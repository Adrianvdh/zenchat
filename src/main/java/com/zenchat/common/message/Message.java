package com.zenchat.common.message;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Message<T> implements Serializable {
    private final String identifier;

    private T payload;
    private Class payloadType;
    private String correlationId;

    private Headers headers;

    public Message(T payload) {
        this(UUID.randomUUID().toString(), payload, null, new Headers());
    }

    public Message(T payload, Headers headers) {
        this(UUID.randomUUID().toString(), payload, null, headers);
    }

    public Message(T payload, String correlationId) {
        this(UUID.randomUUID().toString(), payload, correlationId, new Headers());
    }

    public Message(String identifier, T payload, String correlationId, Headers headers) {
        this.identifier = identifier;
        this.payload = payload;
        this.payloadType = payload.getClass();
        this.correlationId = correlationId;
        this.headers = headers;
    }
}
