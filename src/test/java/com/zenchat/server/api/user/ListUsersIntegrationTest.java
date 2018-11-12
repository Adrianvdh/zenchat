package com.zenchat.server.api.user;

import com.zenchat.client.Client;
import com.zenchat.common.message.Headers;
import com.zenchat.common.message.Message;
import com.zenchat.model.api.listuser.ListUsersRequest;
import com.zenchat.model.api.listuser.UserListResponse;
import com.zenchat.model.api.login.LoginUserRequest;
import com.zenchat.model.api.login.UserLoginResponse;
import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.server.api.AbstractIntegrationTest;
import com.zenchat.server.api.user.repository.UserRepository;
import com.zenchat.server.repository.Repositories;
import com.zenchat.server.security.AuthorizationException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.zenchat.common.message.HeadersProperties.SESSION_ID;
import static com.zenchat.server.api.user.UserConstants.*;

public class ListUsersIntegrationTest extends AbstractIntegrationTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Repositories.getRepository(UserRepository.class);
        userRepository.deleteAll();
    }

    @Test
    public void testListRegisteredUsers_requiresAuthentication_expectUserListResponse() {
        Client client = new Client();
        client.connect(HOST, PORT);

        registerUser(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, client);

        String sessionId = loginUser(USERNAME, PASSWORD, client);

        Headers headers = new Headers();
        headers.addHeader(SESSION_ID, sessionId);
        Message<ListUsersRequest> requestMessage = new Message<>(new ListUsersRequest(), headers);

        Message<UserListResponse> responseMessage = client.send(requestMessage, t -> Assert.fail(t.getMessage()));
        UserListResponse response = responseMessage.getPayload();

        Assert.assertThat(response.getUsers(), Matchers.contains(USERNAME));

        client.disconnect();
    }


    @Test
    public void testListRegisteredUsers_requiresAuthentication_userNotLoggedIn_expectUnauthorized() {
        Client client = new Client();
        client.connect(HOST, PORT);

        Message<ListUsersRequest> requestMessage = new Message<>(new ListUsersRequest());

        final Throwable[] error = new Throwable[1];
        client.send(requestMessage, t -> error[0] = t);

        Assert.assertTrue(error[0].getClass() == AuthorizationException.class);

        client.disconnect();
    }


    private String loginUser(String username, String password, Client client) {
        Message<LoginUserRequest> requestMessage = new Message<>(new LoginUserRequest(username, password));
        Message<UserLoginResponse> responseMessage = client.send(requestMessage, t -> Assert.fail(t.getMessage()));

        return responseMessage.getPayload().getSessionId();
    }

    private void registerUser(String firstName, String lastName, String username, String password, Client client) {
        Message<RegisterUserRequest> requestMessage = new Message<>(new RegisterUserRequest(firstName, lastName, username, password));

        client.send(requestMessage, throwable -> Assert.fail(throwable.getMessage()));
    }

}
