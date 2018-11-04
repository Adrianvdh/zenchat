package com.zenchat.server.api.user;

import com.zenchat.client.Client;
import com.zenchat.common.message.Headers;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.listuser.ListUsersRequest;
import com.zenchat.model.api.listuser.UserListResponse;
import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.exception.RegistrationException;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;
import com.zenchat.server.security.AuthorizationException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.zenchat.common.message.HeadersProperties.SESSION_ID;
import static com.zenchat.server.api.user.UserConstants.NAME;
import static com.zenchat.server.api.user.UserConstants.PASSWORD;
import static com.zenchat.server.api.user.UserConstants.USERNAME;

public class ListUsersIntegrationTest extends AbstractIntegrationTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Repositories.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @Test
    public void testListRegisteredUsers_requiresAuthentication_expectUserListResponse() throws ExecutionException, InterruptedException {
        Client client = new Client(HOST, PORT);
        client.connect();

        registerUser(USERNAME, PASSWORD, NAME, client);

        String sessionId = loginUser(USERNAME, PASSWORD, client);

        Headers headers = new Headers();
        headers.addHeader(SESSION_ID, sessionId);
        Message<ListUsersRequest> requestMessage = new Message<>(new ListUsersRequest(), headers);

        Future<Message<UserListResponse>> responseMessageFuture = client.send(requestMessage, t -> Assert.fail(t.getMessage()));
        Message<UserListResponse> userListResponseMessage = responseMessageFuture.get();
        UserListResponse response = userListResponseMessage.getPayload();

        Assert.assertThat(response.getUsers(), Matchers.contains(USERNAME));

        client.disconnect();
    }


    @Test
    public void testListRegisteredUsers_requiresAuthentication_userNotLoggedIn_expectUnauthorized() throws ExecutionException, InterruptedException {
        Client client = new Client(HOST, PORT);
        client.connect();

        Message<ListUsersRequest> requestMessage = new Message<>(new ListUsersRequest());

        final Throwable[] error = new Throwable[1];
        client.send(requestMessage, t -> error[0] = t).get();

        Assert.assertTrue(error[0].getClass().isAssignableFrom(AuthorizationException.class));

        client.disconnect();
    }


    private String loginUser(String username, String password, Client client) throws ExecutionException, InterruptedException {
        Message<LoginUserRequest> requestMessage = new Message<>(new LoginUserRequest(username, password));
        Future<Message<UserLoginResponse>> responseMessageFuture = client.send(requestMessage, t -> Assert.fail(t.getMessage()));

        Message<UserLoginResponse> responseMessage = responseMessageFuture.get();
        return responseMessage.getPayload().getSessionId();
    }

    private void registerUser(String username, String password, String name, Client client) throws ExecutionException, InterruptedException {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(name, username, password));

        Future<Message<UserRegisterResponse>> responseMessageFuture = client.send(requestMessage, t -> {
            Assert.fail(t.getMessage());
        });

        responseMessageFuture.get();
    }

}
