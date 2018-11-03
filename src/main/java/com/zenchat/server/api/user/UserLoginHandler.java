package com.zenchat.server.api.user;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.server.api.user.exception.AuthenticationException;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;

public class UserLoginHandler implements MessageHandler<UserLoginResponse, LoginUserRequest> {

    private UserRepository userRepository;

    public UserLoginHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginResponse handle(LoginUserRequest request) {
        if(!userRepository.exists(request.getUsername())) {
            throw new AuthenticationException(String.format("User with username '%s' does not exists!", request.getUsername()));
        }

        return new UserLoginResponse("Token");
    }
}
