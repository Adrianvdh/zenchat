package com.zenchat.server.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageHandlerProvider <R, H extends MessageHandler> {
    private Class request;
    private H messageHandler;
}
