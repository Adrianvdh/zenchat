package com.zenchat.server.api.user;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.exception.AuthenticationException;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.zenchat.server.api.user.UserConstants.*;

public class LoginIntegrationTest extends AbstractIntegrationTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Repositories.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @Test
    public void testLoginUser_userLoginsIn_expectSessionTokenResponse() throws ExecutionException, InterruptedException {
        Client client = new Client();
        client.connect(HOST, PORT);

        registerUser(USERNAME, PASSWORD, NAME, client);

        Message<LoginUserRequest> requestMessage = new Message<>(new LoginUserRequest(USERNAME, PASSWORD));
        Future<Message<UserLoginResponse>> responseMessageFuture = client.send(requestMessage, t -> Assert.fail(t.getMessage()));

        Message<UserLoginResponse> responseMessage = responseMessageFuture.get();
        UserLoginResponse userLoginResponse = responseMessage.getPayload();

        String requestId = requestMessage.getIdentifier();
        String correlationId = responseMessage.getCorrelationId();
        Assert.assertEquals(requestId, correlationId);

        Assert.assertNotNull(userLoginResponse.getSessionId());

        client.disconnect();
    }

    @Test
    public void testLoginUser_userNotExist_expectNoTokenResponse() throws ExecutionException, InterruptedException {
        Client client = new Client();
        client.connect(HOST, PORT);

        Message<LoginUserRequest> requestMessage = new Message<>(new LoginUserRequest(USERNAME, PASSWORD));

        final Throwable[] error = new Throwable[1];
        client.send(requestMessage, t -> error[0] = t).get();

        Assert.assertTrue(error[0].getCause().getClass() == AuthenticationException.class);
        client.disconnect();
    }

    private void registerUser(String username, String password, String name, Client client) throws ExecutionException, InterruptedException {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(name, username, password));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, t -> {
            Assert.fail(t.getMessage());
        });

        responseMessageFuture.get();
    }

}
