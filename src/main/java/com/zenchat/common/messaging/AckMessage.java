package com.zenchat.common.messaging;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class AckMessage implements Serializable {
    private final String identifier;
    private final Long timestamp;

    public AckMessage(String identifier) {
        this.identifier = identifier;
        this.timestamp = System.currentTimeMillis();
    }

}
