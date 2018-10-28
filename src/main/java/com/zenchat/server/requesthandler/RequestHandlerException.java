package com.zenchat.server.requesthandler;

public class RequestHandlerException extends RuntimeException {
    public RequestHandlerException(String message) {
        super(message);
    }
}
