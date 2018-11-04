package com.zenchat.server.security;

public enum SecurityRole {
    USER("USER");

    private String description;

    SecurityRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
