package com.zenchat.server.requesthandler;

import com.zenchat.model.api.registration.RegisterUserRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerRegister {
    private Map<Class, RequestHandler> handlers = new HashMap<>();

    {
        handlers.put(RegisterUserRequest.class, new UserRegistrationHandler());
    }

    public RequestHandler getHandler(Class requestClass) {
        if(requestClass == null || !handlers.containsKey(requestClass)) {
            throw new RequestHandlerException(String.format("Request requestClass could not be found for request '%s'",
                    requestClass == null ? "Null" : requestClass.getSimpleName()));
        }
        return handlers.get(requestClass);
    }
}
