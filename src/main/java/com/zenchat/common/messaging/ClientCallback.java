package com.zenchat.common.messaging;

public interface ClientCallback {

    void onAck(AckMessage ackMessage);
    void onError(Throwable throwable);
}
