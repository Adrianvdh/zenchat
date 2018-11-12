package com.zenchat.server.api.user;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.user.exception.RegistrationException;
import com.zenchat.server.api.user.model.User;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;
import com.zenchat.server.security.SecurityRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class UserRegistrationHandler implements MessageHandler<UserRegisterResponse, RegisterUserRequest> {

    private UserRepository userRepository;

    public UserRegistrationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {
        try {
            log.info("Sleeping...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(registerUserRequest.getUsername()));
        if (userOptional.isPresent()) {
            throw new RegistrationException("This user is already registered!");
        }

        userRepository.save(new User(
                UUID.randomUUID().toString(),
                registerUserRequest.getFirstName(),
                registerUserRequest.getLastName(),
                registerUserRequest.getUsername(),
                registerUserRequest.getPassword(),
                SecurityRole.USER
        ));

        return new UserRegisterResponse(registerUserRequest.getFirstName(), registerUserRequest.getLastName(), registerUserRequest.getUsername());
    }
}