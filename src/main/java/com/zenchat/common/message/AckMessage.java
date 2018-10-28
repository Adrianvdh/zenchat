package com.zenchat.common.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class AckMessage implements Serializable {
    private final String identifier;
    private final Long timestamp;

    public AckMessage(String identifier) {
        this.identifier = identifier;
        this.timestamp = System.currentTimeMillis();
    }

}
