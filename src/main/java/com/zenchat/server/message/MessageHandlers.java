package com.zenchat.server.message;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageHandlers {
    private static MessageHandlers INSTANCE;

    private Map<Class, MessageHandlerProvider> handlerProviders = new HashMap<>();

    protected MessageHandlers() {
    }

    public static MessageHandlerProvider getHandler(Class requestClass) {
        if (requestClass == null || !getInstance().handlerProviders.containsKey(requestClass)) {
            String message = String.format("Message handler could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new MessageHandlerException(message);
        }
        return getInstance().handlerProviders.get(requestClass);
    }

    protected  <T extends MessageHandlerProvider> void registerOne(Class request, T messageHandlerProvider) {
        this.handlerProviders.put(request, messageHandlerProvider);

        log.info("Registered message handler '{}'", messageHandlerProvider.getMessageHandler().getClass().getSimpleName());
    }

    protected static void constructSingleton(MessageHandlers messageHandlers) {
        INSTANCE = messageHandlers;
    }

    private static MessageHandlers getInstance() {
        return INSTANCE;
    }
}
