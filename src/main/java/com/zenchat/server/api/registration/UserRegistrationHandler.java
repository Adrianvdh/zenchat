package com.zenchat.server.api.registration;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.message.MessageHandler;

public class UserRegistrationHandler implements MessageHandler<UserRegisterResponse, RegisterUserRequest> {

    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {
        return new UserRegisterResponse(registerUserRequest.getUsername(), registerUserRequest.getName(), true, null);
    }
}
