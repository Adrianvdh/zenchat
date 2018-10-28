package com.zenchat.server.message.protocol;

import com.zenchat.common.message.AckMessage;
import com.zenchat.common.message.Headers;
import com.zenchat.common.message.Message;
import com.zenchat.common.message.protocol.Initialize;

import java.util.UUID;

import static com.zenchat.common.message.HeadersProperties.SESSION_ID;

public class InitializeMessageHandler implements ProtocolMessageHandler<AckMessage, Initialize> {

    @Override
    public Message<AckMessage> handle(Message<Initialize> message) {
        AckMessage ack = new AckMessage(message.getIdentifier());

        Headers headers = new Headers();
        headers.addHeader(SESSION_ID, UUID.randomUUID().toString());

        Message<AckMessage> ackMessageMessage = new Message<>(UUID.randomUUID().toString(), ack, null, headers);
        return ackMessageMessage;
    }
}
