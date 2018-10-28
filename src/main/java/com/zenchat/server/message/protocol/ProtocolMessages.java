package com.zenchat.server.message.protocol;

import com.zenchat.common.message.protocol.Initialize;

import java.util.HashMap;
import java.util.Map;

public class ProtocolMessages {

    private static Map<Class, ProtocolMessageHandler> protocolMessages = new HashMap<>();

    static {
        protocolMessages.put(Initialize.class, new InitializeMessageHandler());
    }

    public static boolean isProtocolMessage(Class message) {
        return protocolMessages.containsKey(message);
    }

    public static ProtocolMessageHandler getHandler(Class protocolMessage) {
        return protocolMessages.get(protocolMessage);
    }
}
