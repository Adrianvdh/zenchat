package com.zenchat.server.requesthandler;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.api.registration.UserRegistrationHandler;
import org.junit.Assert;
import org.junit.Test;

public class RequestHandlerDelegationTest {

    @Test
    public void testHandleMessageDelegation() {
        UserRegistrationHandler userRegistrationHandler = new UserRegistrationHandler();

        UserRegisterResponse expectedResponse = new UserRegisterResponse("username", "name", true, null);
        UserRegisterResponse response = userRegistrationHandler.handle(new RegisterUserRequest("username", "password", "name"));

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void testChooseMessageHandlerByRequest_expectCorrectHandlerSelected() {
        RequestHandlerRegister messageHandlerRegister = new RequestHandlerRegister();

        RequestHandler registrationHandler = messageHandlerRegister.getHandler(RegisterUserRequest.class);

        Assert.assertTrue(registrationHandler.getClass().isAssignableFrom(UserRegistrationHandler.class));
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerNotFound_expectHandlerNotFoundException() {
        RequestHandlerRegister messageHandlerRegister = new RequestHandlerRegister();

        messageHandlerRegister.getHandler(Object.class);
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerIsNull_expectHandlerNotFoundException() {
        RequestHandlerRegister messageHandlerRegister = new RequestHandlerRegister();

        messageHandlerRegister.getHandler(null);
    }

    @Test
    public void testGetMessageHandlerRegisterSingleton() {
        RequestHandlerRegisterSingleton instance = RequestHandlerRegisterSingleton.getInstance();

        RequestHandlerRegister handlerRegister = instance.getHandlerRegister();

        Assert.assertNotNull(handlerRegister);
    }
}
