package com.zenchat.server.requesthandler;

public class RequestHandlerRegisterSingleton {
    private static RequestHandlerRegisterSingleton messageHandlerRegisterSingleton = new RequestHandlerRegisterSingleton();
    private RequestHandlerRegister messageHandlerRegister = new RequestHandlerRegister();

    private RequestHandlerRegisterSingleton() {
    }

    public static RequestHandlerRegisterSingleton getInstance() {
        return messageHandlerRegisterSingleton;
    }

    public RequestHandlerRegister getHandlerRegister() {
        return messageHandlerRegister;
    }
}
