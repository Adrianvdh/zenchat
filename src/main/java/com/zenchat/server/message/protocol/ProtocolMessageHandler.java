package com.zenchat.server.message.protocol;

import com.zenchat.common.message.Message;

public interface ProtocolMessageHandler<R, T> {

    Message<R> handle(Message<T> message);
}
