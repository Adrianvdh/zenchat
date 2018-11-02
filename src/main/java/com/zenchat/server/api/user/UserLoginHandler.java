package com.zenchat.server.api.user;

import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.message.MessageHandler;

public class UserLoginHandler implements MessageHandler<UserLoginResponse, LoginUserRequest> {

    private UserRepository userRepository;

    public UserLoginHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginResponse handle(LoginUserRequest request) {
        return new UserLoginResponse("Token");
    }
}
