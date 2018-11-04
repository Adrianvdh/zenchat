package com.zenchat.ui.app.chat.model;

public class User {
    private String name;
    private Status status;

    public User() {
    }

    public User(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ONLINE,
        OFFLINE
    }
}
