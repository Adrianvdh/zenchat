package com.zenchat.server.requesthandler;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;

public class UserRegistrationHandler implements RequestHandler<UserRegisterResponse, RegisterUserRequest> {
    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {
        return new UserRegisterResponse(registerUserRequest.getUsername(), registerUserRequest.getName(), true, null);

    }
}
