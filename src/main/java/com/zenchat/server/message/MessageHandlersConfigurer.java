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
        UserRepository userRepository = Repositories.getRepository(UserRepository.class);

        messageHandlers.registerOne(RegisterUserRequest.class, new UserRegistrationHandler(userRepository));
        messageHandlers.registerOne(LoginUserRequest.class, new UserLoginHandler(userRepository));

        MessageHandlers.constructSingleton(messageHandlers);
    }
}
