package com.zenchat;

import com.zenchat.client.Client;
import com.zenchat.common.messaging.Message;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RegistrationTest {

    private static String HOST = "localhost";
    private static int PORT = 34567;

    private Server server;

    @Before
    public void setUp() {
        server = new Server(PORT);
        server.start();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testRegisterUser_expectUserRegistrationSuccess() throws ExecutionException, InterruptedException {
        Client client = new Client(HOST, PORT);
        client.connect();

        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest("username", "Test1234", "Test User"));
        String requestId = requestMessage.getIdentifier();

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage);

        Message<UserRegisterResponse> responseMessage = responseMessageFuture.get();
        UserRegisterResponse userRegisterResponse = responseMessage.getPayload();

        String correlationId = responseMessage.getCorrelationId();

        Assert.assertTrue(userRegisterResponse.isSuccess());
        Assert.assertEquals(requestId, correlationId);

        client.disconnect();
    }

}
