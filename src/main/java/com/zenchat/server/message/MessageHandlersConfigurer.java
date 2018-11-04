package com.zenchat.server.message;

import com.zenchat.server.config.MessageHandlerConfiguration;

public class MessageHandlersConfigurer {
    public static void setupMessageHandlers() {
        MessageHandlers messageHandlers = new MessageHandlers();

        MessageHandlerRegistry messageHandlerRegistry = MessageHandlerConfiguration.configureMessageHandlers();
        messageHandlerRegistry.getMessageHandlerProviders().forEach(messageHandlerProvider -> {
            messageHandlers.registerOne(messageHandlerProvider.getRequestClass(), messageHandlerProvider.getMessageHandler());
        });

        MessageHandlers.constructSingleton(messageHandlers);
    }
}
