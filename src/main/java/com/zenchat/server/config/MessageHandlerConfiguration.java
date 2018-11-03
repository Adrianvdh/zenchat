package com.zenchat.server.config;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.user.UserLoginHandler;
import com.zenchat.server.api.user.UserRegistrationHandler;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandlerProvider;
import com.zenchat.server.message.MessageHandlerRegistry;
import com.zenchat.server.repository.Repositories;

public class MessageHandlerConfiguration {

    public static MessageHandlerRegistry configureMessageHandlers() {
        UserRepository userRepository = Repositories.getRepository(UserRepository.class);

        return MessageHandlerRegistry.fromProviders(
                new MessageHandlerProvider<>(RegisterUserRequest.class, new UserRegistrationHandler(userRepository)),
                new MessageHandlerProvider<>(LoginUserRequest.class, new UserLoginHandler(userRepository))
        );
    }
}
