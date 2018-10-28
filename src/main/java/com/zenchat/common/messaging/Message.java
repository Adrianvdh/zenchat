package com.zenchat.common.messaging;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Message<T> implements Serializable {
    private final String identifier;

    private T payload;
    private Class payloadType;

    private Headers headers;

    public Message(T payload) {
        this(UUID.randomUUID().toString(), payload, new Headers());
    }

    public Message(T payload, Headers headers) {
        this(UUID.randomUUID().toString(), payload, headers);
    }

    public Message(String identifier, T payload, Headers headers) {
        this.identifier = identifier;
        this.payload = payload;
        this.headers = headers;
        this.payloadType = this.payload.getClass();
    }
}
