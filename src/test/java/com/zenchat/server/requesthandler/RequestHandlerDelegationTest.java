package com.zenchat.server.requesthandler;

import com.zenchat.model.api.registration.RegisterUserRequest;
import com.zenchat.model.api.registration.UserRegisterResponse;
import com.zenchat.server.ApplicationBootstrapper;
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
        RequestHandler registrationHandler = ApplicationBootstrapper.getHandler(RegisterUserRequest.class);

        Assert.assertTrue(registrationHandler.getClass().isAssignableFrom(UserRegistrationHandler.class));
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerNotFound_expectHandlerNotFoundException() {
        ApplicationBootstrapper.getHandler(Object.class);
    }

    @Test(expected = RequestHandlerException.class)
    public void testChooseMessageHandler_handlerIsNull_expectHandlerNotFoundException() {
        ApplicationBootstrapper.getHandler(null);
    }
}
