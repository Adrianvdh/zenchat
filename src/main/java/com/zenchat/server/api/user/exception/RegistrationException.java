package com.zenchat.server.api.user.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RegistrationException extends RuntimeException {
    private Map<String, String> errors = new HashMap<>();

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Map<String, String> errors) {
        super(message);
    }

}
