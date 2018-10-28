package com.zenchat.server.message;

import com.zenchat.common.messaging.Message;

public interface RequestHandler<R, T> {

    R handle(T request);
}
