package com.zenchat.server.message;

public class RequestHandlerException extends RuntimeException {
    public RequestHandlerException(String message) {
        super(message);
    }
}