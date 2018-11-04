package com.zenchat.server.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageHandlerProvider <R, H extends MessageHandler<?, R>> {
    private Class<R> requestClass;
    private H messageHandler;
}
