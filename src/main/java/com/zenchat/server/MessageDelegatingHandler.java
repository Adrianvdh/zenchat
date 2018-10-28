package com.zenchat.server;

import com.zenchat.common.messaging.AckMessage;
import com.zenchat.common.messaging.Headers;
import com.zenchat.common.messaging.Message;
import com.zenchat.common.messaging.protocol.Initialize;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import static com.zenchat.common.messaging.MessageHeadersProperties.SESSION_ID;

public class MessageDelegatingHandler {

    private ObjectOutputStream out;

    public MessageDelegatingHandler(ObjectOutputStream out) {
        this.out = out;
    }

    public void handle(Message message) {
        Class payloadType = message.getPayloadType();
        try {
            if (payloadType == Initialize.class) {
                AckMessage ack = new AckMessage(message.getIdentifier());

                Headers headers = new Headers();
                headers.addHeader(SESSION_ID, UUID.randomUUID().toString());

                Message<AckMessage> ackMessageMessage = new Message<>(UUID.randomUUID().toString(), ack, headers);

                reply(ackMessageMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void reply(Message<T> message) throws IOException {
        out.writeObject(message);
    }
}
