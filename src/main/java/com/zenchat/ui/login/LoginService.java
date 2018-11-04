package com.zenchat.ui.login;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginService {

    private Client client;

    public LoginService(Client client) {
        this.client = client;
    }

    public void loginUser(String username, String password) {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(username, password, "name"));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, throwable -> {
            System.out.println("Error occurred" + throwable.getMessage());
            return;
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
        System.out.println("registered " + userRegisterResponse.getUsername());

    }
}
