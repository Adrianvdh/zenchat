package com.zenchat.server.message;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.user.UserLoginHandler;
import com.zenchat.server.api.user.UserRegistrationHandler;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;

public class MessageHandlersConfigurer {
    public static void setupMessageHandlers() {
        MessageHandlers messageHandlers = new MessageHandlers();

        messageHandlers.registerOne(RegisterUserRequest.class, new UserRegistrationHandler(Repositories.getRepository(UserRepository.class)));
        messageHandlers.registerOne(LoginUserRequest.class, new UserLoginHandler(Repositories.getRepository(UserRepository.class)));

        MessageHandlers.constructSingleton(messageHandlers);
    }
}
