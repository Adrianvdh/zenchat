package com.zenchat.server.api.user;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.user.exception.RegistrationException;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;

import java.util.Optional;
import java.util.UUID;

public class UserRegistrationHandler implements MessageHandler<UserRegisterResponse, RegisterUserRequest> {

    private UserRepository userRepository;

    public UserRegistrationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(registerUserRequest.getUsername()));
        if (userOptional.isPresent()) {
            throw new RegistrationException("This user is already registered!");
        }

        userRepository.save(new User(UUID.randomUUID().toString(), registerUserRequest.getUsername(), registerUserRequest.getPassword()));

        return new UserRegisterResponse(registerUserRequest.getName(), registerUserRequest.getUsername());
    }
}
