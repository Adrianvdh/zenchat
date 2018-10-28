package com.zenchat.common.messaging.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FetchResponse implements Serializable {
    private String topic;
    private Integer topicSize;
}
