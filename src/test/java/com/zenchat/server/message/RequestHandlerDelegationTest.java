package com.zenchat.server.message;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.user.UserRegistrationHandler;
import com.zenchat.server.api.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static com.zenchat.server.api.user.UserConstants.NAME;
import static com.zenchat.server.api.user.UserConstants.PASSWORD;
import static com.zenchat.server.api.user.UserConstants.USERNAME;

public class RequestHandlerDelegationTest {

    @Test
    public void testHandleMessageDelegation() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findByUsername(USERNAME)).thenReturn(null);

        UserRegistrationHandler userRegistrationHandler = new UserRegistrationHandler(mockUserRepository);

        UserRegisterResponse expectedResponse = new UserRegisterResponse(NAME, USERNAME);
        UserRegisterResponse response = userRegistrationHandler.handle(new RegisterUserRequest(NAME, USERNAME, PASSWORD));

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void testChooseMessageHandlerByRequest_expectCorrectHandlerSelected() {
        MessageHandler registrationHandler = MessageHandlers.getHandler(RegisterUserRequest.class);

        Assert.assertTrue(registrationHandler.getClass().isAssignableFrom(UserRegistrationHandler.class));
    }

    @Test(expected = MessageHandlerException.class)
    public void testChooseMessageHandler_handlerNotFound_expectHandlerNotFoundException() {
        MessageHandlers.getHandler(Object.class);
    }

    @Test(expected = MessageHandlerException.class)
    public void testChooseMessageHandler_handlerIsNull_expectHandlerNotFoundException() {
        MessageHandlers.getHandler(null);
    }
}
