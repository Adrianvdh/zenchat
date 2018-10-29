package com.zenchat.server.api.registration;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.registration.repository.User;
import com.zenchat.server.api.registration.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;

public class UserRegistrationHandler implements MessageHandler<UserRegisterResponse, RegisterUserRequest> {

    private UserRepository userRepository;

    public UserRegistrationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {
        if(userRepository.exists(registerUserRequest.getUsername())) {
            throw new RegistrationException("Registration failed");
        }
        userRepository.save(new User(registerUserRequest.getUsername()));

        return new UserRegisterResponse(registerUserRequest.getUsername(), registerUserRequest.getName());
    }
}
