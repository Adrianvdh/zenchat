package com.zenchat.server.message;

public interface MessageHandler<R, T> {

    R handle(T request);
}
