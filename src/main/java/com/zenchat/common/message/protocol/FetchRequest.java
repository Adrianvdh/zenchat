package com.zenchat.common.messaging.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FetchRequest implements Serializable {
    private String topic;
}
