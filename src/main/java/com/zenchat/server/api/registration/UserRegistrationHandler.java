package com.zenchat.server.api.registration;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.requesthandler.RequestHandler;

public class UserRegistrationHandler implements RequestHandler<UserRegisterResponse, RegisterUserRequest> {

    @Override
    public UserRegisterResponse handle(RegisterUserRequest registerUserRequest) {
        return new UserRegisterResponse(registerUserRequest.getUsername(), registerUserRequest.getName(), true, null);
    }
}
