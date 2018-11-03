package com.zenchat.server.message;

import java.util.Arrays;
import java.util.List;

public class MessageHandlerRegistry {

    private List<MessageHandlerProvider> messageHandlerProviders;

    private MessageHandlerRegistry(List<MessageHandlerProvider> messageHandlerProviders) {
        this.messageHandlerProviders = messageHandlerProviders;
    }

    public static MessageHandlerRegistry fromProviders(MessageHandlerProvider... providers) {
        return new MessageHandlerRegistry(Arrays.asList(providers));
    }

    protected List<MessageHandlerProvider> getMessageHandlerProviders() {
        return messageHandlerProviders;
    }
}
