package com.zenchat.server.api.user;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.exception.RegistrationException;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static com.zenchat.server.api.user.UserConstants.*;

public class RegistrationIntegrationTest extends AbstractIntegrationTest {

    private Client client;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Repositories.getRepository(UserRepository.class);
        userRepository.deleteAll();

        client = new Client();
        client.connect(HOST, PORT);
    }

    @After
    public void tearDown() throws Exception {
        client.disconnect();
    }

    @Test
    public void testRegisterUser_expectUserRegistrationSuccess() {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD));
        Message<UserRegisterResponse> responseMessage = client.send(requestMessage, throwable -> {
            Assert.fail(throwable.getMessage());
        });
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();

        String requestId = requestMessage.getIdentifier();
        String correlationId = responseMessage.getCorrelationId();
        Assert.assertEquals(requestId, correlationId);

        Assert.assertEquals(USERNAME, userRegisterResponse.getUsername());
    }

    @Test
    public void testRegisterUser_userExists_expectConflict() {
        // given a user is registered
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD));
        client.send(requestMessage, throwable -> {
            Assert.fail(throwable.getMessage());
        });

        final Throwable[] error = {null};
        // when the client issues the same login command
        client.send(requestMessage, throwable -> error[0] = throwable);

        // expect an RegistrationException to occur
        Assert.assertTrue(error[0].getClass().isAssignableFrom(RegistrationException.class));
    }
}
