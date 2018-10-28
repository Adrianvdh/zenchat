package com.zenchat.server.message;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.ZenChatServer;
import com.zenchat.server.api.registration.UserRegistrationHandler;
import com.zenchat.server.api.registration.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RequestHandlerDelegationTest {

    @Test
    public void testHandleMessageDelegation() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.exists("username")).thenReturn(false);

        UserRegistrationHandler userRegistrationHandler = new UserRegistrationHandler(mockUserRepository);

        UserRegisterResponse expectedResponse = new UserRegisterResponse("username", "name", true, null);
        UserRegisterResponse response = userRegistrationHandler.handle(new RegisterUserRequest("username", "password", "name"));

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void testChooseMessageHandlerByRequest_expectCorrectHandlerSelected() {
        MessageHandler registrationHandler = ZenChatServer.getHandler(RegisterUserRequest.class);

        Assert.assertTrue(registrationHandler.getClass().isAssignableFrom(UserRegistrationHandler.class));
    }

    @Test(expected = MessageHandlerException.class)
    public void testChooseMessageHandler_handlerNotFound_expectHandlerNotFoundException() {
        ZenChatServer.getHandler(Object.class);
    }

    @Test(expected = MessageHandlerException.class)
    public void testChooseMessageHandler_handlerIsNull_expectHandlerNotFoundException() {
        ZenChatServer.getHandler(null);
    }
}
