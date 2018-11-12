package com.zenchat.ui.app.registration;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class RegistrationService {

    private Client client;
    private Consumer<Throwable> exceptionHandler;
    private Consumer<UserRegisterResponse> successHandler;

    public RegistrationService(Client client) {
        this.client = client;
    }

    public void registerUser(String firstName, String lastName, String username, String password) {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(firstName, lastName, username, password));
        Message<UserRegisterResponse> responseMessage = client.send(requestMessage, throwable -> {
            if (exceptionHandler != null) {
                this.exceptionHandler.accept(throwable);
            }
        });

        if (responseMessage == null) {
            return;
        }
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();
        log.info("{} was registered successfully!", userRegisterResponse.getFirstName());

        if (successHandler != null) {
            successHandler.accept(userRegisterResponse);
        }
    }

    public void onError(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void onSuccess(Consumer<UserRegisterResponse> successHandler) {
        this.successHandler = successHandler;
    }
}
