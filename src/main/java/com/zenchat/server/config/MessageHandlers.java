package com.zenchat.server.config;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.user.UserLoginHandler;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.api.user.UserRegistrationHandler;
import com.zenchat.server.message.MessageHandler;
import com.zenchat.server.message.MessageHandlerException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.zenchat.server.repository.Repositories.getRepository;

@Slf4j
public class MessageHandlers {
    private static MessageHandlers INSTANCE = new MessageHandlers();

    private Map<Class, MessageHandler> handlers = new HashMap<>();

    public MessageHandlers() {
        registerRequestHandlers();
    }

    public static MessageHandlers getInstance() {
        return INSTANCE;
    }

    private void registerRequestHandlers() {
        handlers.put(RegisterUserRequest.class, new UserRegistrationHandler(getRepository(UserRepository.class)));
        handlers.put(LoginUserRequest.class, new UserLoginHandler(getRepository(UserRepository.class)));

        handlers.forEach((klass, handler) -> log.info("Registered handler '{}'", handler.getClass().getSimpleName()));
    }

    public static MessageHandler getHandler(Class requestClass) {
        if (requestClass == null || !getInstance().handlers.containsKey(requestClass)) {
            String message = String.format("Message handler could not be found for request '%s'", requestClass == null ? "Null" : requestClass.getSimpleName());
            log.error(message);

            throw new MessageHandlerException(message);
        }
        return getInstance().handlers.get(requestClass);
    }
}
