package com.zenchat.server.message;

import com.zenchat.common.message.Headers;
import com.zenchat.common.message.Message;
import com.zenchat.server.ZenChatServer;
import com.zenchat.server.message.protocol.ProtocolMessageHandler;
import com.zenchat.server.message.protocol.ProtocolMessages;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

@Slf4j
public class MessageDelegatingHandler {

    private ObjectOutputStream out;

    public MessageDelegatingHandler(ObjectOutputStream out) {
        this.out = out;
    }

    public void handle(Message message) {
        Class payloadType = message.getPayloadType();
        try {

            if (ProtocolMessages.isProtocolMessage(payloadType)) {
                ProtocolMessageHandler handler = ProtocolMessages.getHandler(payloadType);
                Message responseMessage = handler.handle(message);

                reply(responseMessage);

            } else {
                MessageHandler handler = ZenChatServer.getHandler(payloadType);
                Object response = handler.handle(message.getPayload());

                Message responseMessage = new Message<>(UUID.randomUUID().toString(), response, message.getIdentifier(), new Headers());
                reply(responseMessage);

            }
        } catch (Throwable e) {
            log.error("An exception occurred", e);
            try {
                replyThrowable(new Message<>(e, message.getIdentifier()));
            } catch (IOException ioe) {
                log.error("A connection exception occurred!", ioe);
            }
        }
    }

    private <T> void reply(Message<T> message) throws IOException {
        out.writeObject(message);
    }

    private void replyThrowable(Message<Throwable> message) throws IOException {
        out.writeObject(message);
    }
}
