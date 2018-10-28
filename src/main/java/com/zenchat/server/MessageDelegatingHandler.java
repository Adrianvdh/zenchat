package com.zenchat.server;

import com.zenchat.common.messaging.AckMessage;
import com.zenchat.common.messaging.Headers;
import com.zenchat.common.messaging.Message;
import com.zenchat.common.messaging.protocol.Initialize;
import com.zenchat.server.requesthandler.RequestHandlerRegister;
import com.zenchat.server.requesthandler.RequestHandlerRegisterSingleton;
import com.zenchat.server.requesthandler.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import static com.zenchat.common.messaging.MessageHeadersProperties.SESSION_ID;

@Slf4j
public class MessageDelegatingHandler {

    private RequestHandlerRegister messageHandlerRegister = RequestHandlerRegisterSingleton.getInstance().getHandlerRegister();

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

                Message<AckMessage> ackMessageMessage = new Message<>(UUID.randomUUID().toString(), ack, null, headers);

                reply(ackMessageMessage);

            } else {
                RequestHandler handler = messageHandlerRegister.getHandler(payloadType);
                Object response = handler.handle(message.getPayload());

                Message responseMessage = new Message<>(UUID.randomUUID().toString(), response, message.getIdentifier(), new Headers());
                reply(responseMessage);

            }
        } catch (Throwable e) {
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
