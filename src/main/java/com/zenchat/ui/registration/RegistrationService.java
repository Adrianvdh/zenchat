package com.zenchat.ui.registration;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RegistrationService {

    private Client client;

    public RegistrationService(Client client) {
        this.client = client;
    }

    public void registerUser(String username, String password) {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(username, password, null));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, throwable -> {

        });

        Message<UserRegisterResponse> responseMessage = null;
        try {
            responseMessage = responseMessageFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();

    }
}