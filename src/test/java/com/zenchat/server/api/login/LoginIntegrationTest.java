package com.zenchat.server.api.login;

import com.zenchat.client.Client;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.ZenChatTestServer;
import com.zenchat.server.api.registration.repository.UserRepository;
import com.zenchat.server.config.ServerConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginIntegrationTest {

    private ZenChatTestServer server;
    private String host = "localhost";
    private int port;

    @Before
    public void setUp() {
        ServerConfiguration serverConfiguration = ServerConfiguration.fromProperties("application.properties");
        server = new ZenChatTestServer(serverConfiguration);
        server.startup();

        port = serverConfiguration.getPort();

        UserRepository userRepository = ZenChatTestServer.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @After
    public void tearDown() {
        server.shutdown();
    }

    @Test
    public void testLoginUser_userLoginsIn_expectSessionTokenResponse() throws ExecutionException, InterruptedException {
        Client client = new Client(host, port);
        client.connect();

        registerUser("adrian", "Test1234", "Adrian van den Houten", client);

        Message<LoginUserRequest> requestMessage = new Message<>(new LoginUserRequest("adrian", "Test1234"));
        Future<Message<UserLoginResponse>> responseMessageFuture = client.send(requestMessage, t -> Assert.fail(t.getMessage()));

        Message<UserLoginResponse> responseMessage = responseMessageFuture.get();
        UserLoginResponse userLoginResponse = responseMessage.getPayload();

        String requestId = requestMessage.getIdentifier();
        String correlationId = responseMessage.getCorrelationId();
        Assert.assertEquals(requestId, correlationId);

        Assert.assertNotNull(userLoginResponse.getToken());

        client.disconnect();
    }

    private void registerUser(String username, String password, String name, Client client) throws ExecutionException, InterruptedException {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(username, password, name));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, t -> {
            Assert.fail(t.getMessage());
        });

        responseMessageFuture.get();
    }

}
