package com.zenchat.server.api.user;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.exception.RegistrationException;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.zenchat.server.api.user.UserConstants.*;

public class RegistrationIntegrationTest extends AbstractIntegrationTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Repositories.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_userExists_expectConflict() throws ExecutionException, InterruptedException {
        Client client = new Client();
        client.connect(HOST, PORT);

        // given a user is registered
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(NAME, USERNAME, PASSWORD));
        client.send(requestMessage, null).get();

        final Throwable[] error = {null};

        // when the client issues the same login command
        client.send(requestMessage, throwable -> error[0] = throwable).get();

        // expect an RegistrationException to occur
        Assert.assertTrue(error[0].getClass().isAssignableFrom(RegistrationException.class));
        client.disconnect();
    }


    @Test
    public void testRegisterUser_expectUserRegistrationSuccess() throws ExecutionException, InterruptedException {
        Client client = new Client();
        client.connect(HOST, PORT);

        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(NAME, USERNAME, PASSWORD));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, t -> {
            Assert.fail(t.getMessage());
        });

        Message<UserRegisterResponse> responseMessage = responseMessageFuture.get();
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();

        String requestId = requestMessage.getIdentifier();
        String correlationId = responseMessage.getCorrelationId();
        Assert.assertEquals(requestId, correlationId);

        Assert.assertEquals(USERNAME, userRegisterResponse.getUsername());

        client.disconnect();
    }

}
