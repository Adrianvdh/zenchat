package com.zenchat.server.message;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageHandlers {
    private static MessageHandlers INSTANCE;

    private Map<Class, MessageHandler> handlers = new HashMap<>();

    public MessageHandlers() {
    }

    private static MessageHandlers getInstance() {
        return INSTANCE;
    }

    protected static void constructSingleton(MessageHandlers messageHandlers) {
        INSTANCE = messageHandlers;
    }

    public static MessageHandler getHandler(Class requestClass) {
        if (requestClass == null || !getInstance().handlers.containsKey(requestClass)) {
            String message = String.format("Message handler could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new MessageHandlerException(message);
        }
        return getInstance().handlers.get(requestClass);
    }

    public <T extends MessageHandler> void registerOne(Class request, T messageHandler) {
        this.handlers.put(request, messageHandler);

        log.info("Registered message handler '{}'", messageHandler.getClass().getSimpleName());
    }
}
