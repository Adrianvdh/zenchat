package com.zenchat.server.api.registration;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.ZenChatTestServer;
import com.zenchat.server.api.registration.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RegistrationIntegrationTest {

    private ZenChatTestServer server = new ZenChatTestServer();
    private static String HOST = "localhost";
    private static int PORT = 34567;

    @Before
    public void setUp() {
        server.startup();

        UserRepository userRepository = ZenChatTestServer.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @After
    public void tearDown() {
        server.shutdown();
    }

    @Test
    public void testRegisterUser_expectUserRegistrationSuccess() throws ExecutionException, InterruptedException {
        Client client = new Client(HOST, PORT);
        client.connect();

        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest("username", "Test1234", "Test User"));
        String requestId = requestMessage.getIdentifier();

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, t -> {
            Assert.fail(t.getMessage());
        });

        Message<UserRegisterResponse> responseMessage = responseMessageFuture.get();
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();

        String correlationId = responseMessage.getCorrelationId();

        Assert.assertTrue(userRegisterResponse.isSuccess());
        Assert.assertEquals(requestId, correlationId);

        client.disconnect();
    }

    @Test
    public void testRegisterUser_userExists_expectConflict() throws ExecutionException, InterruptedException {
        Client client = new Client(HOST, PORT);
        client.connect();

        // given a user is registered
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest("username", "Test1234", "Test User"));
        client.send(requestMessage, null).get();

        final Throwable[] error = {null};

        // when the client issues the same register command
        client.send(requestMessage, throwable -> {
            error[0] = throwable;
        }).get();

        // expect an RegistrationException to occur
        Assert.assertTrue(error[0].getClass().isAssignableFrom(RegistrationException.class));
        client.disconnect();
    }
}
