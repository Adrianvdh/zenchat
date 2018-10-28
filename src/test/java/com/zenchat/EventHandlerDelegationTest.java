package com.zenchat;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.requesthandler.*;
import org.junit.Assert;
import org.junit.Test;

public class EventHandlerDelegationTest {

    @Test
    public void testHandleMessageDelegation() {
        UserRegistrationHandler userRegistrationHandler = new UserRegistrationHandler();

        UserRegisterResponse expectedResponse = new UserRegisterResponse("username", "name", true, null);
        UserRegisterResponse response = userRegistrationHandler.handle(new RegisterUserRequest("username", "password", "name"));

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void testChooseMessageHandlerByRequest_expectCorrectHandlerSelected() {
        MessageHandlerRegister messageHandlerRegister = new MessageHandlerRegister();

        RequestHandler registrationHandler = messageHandlerRegister.getHandler(RegisterUserRequest.class);

        Assert.assertTrue(registrationHandler.getClass().isAssignableFrom(UserRegistrationHandler.class));
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerNotFound_expectHandlerNotFoundException() {
        MessageHandlerRegister messageHandlerRegister = new MessageHandlerRegister();

        messageHandlerRegister.getHandler(Object.class);
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerIsNull_expectHandlerNotFoundException() {
        MessageHandlerRegister messageHandlerRegister = new MessageHandlerRegister();

        messageHandlerRegister.getHandler(null);
    }

    @Test
    public void testGetMessageHandlerRegisterSingleton() {
        MessageHandlerRegisterSingleton instance = MessageHandlerRegisterSingleton.getInstance();

        MessageHandlerRegister handlerRegister = instance.getHandlerRegister();

        Assert.assertNotNull(handlerRegister);
    }
}
