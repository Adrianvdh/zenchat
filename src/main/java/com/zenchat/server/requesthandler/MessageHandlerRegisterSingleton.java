package com.zenchat.server.requesthandler;

public class MessageHandlerRegisterSingleton {
    private static MessageHandlerRegisterSingleton messageHandlerRegisterSingleton = new MessageHandlerRegisterSingleton();
    private MessageHandlerRegister messageHandlerRegister = new MessageHandlerRegister();

    private MessageHandlerRegisterSingleton() {
    }

    public static MessageHandlerRegisterSingleton getInstance() {
        return messageHandlerRegisterSingleton;
    }

    public MessageHandlerRegister getHandlerRegister() {
        return messageHandlerRegister;
    }
}
