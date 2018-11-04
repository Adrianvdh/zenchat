package com.zenchat.ui.app.chat.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private String user;
    private String message;
    private LocalDateTime sent;

    public ChatMessage(String user, String message, LocalDateTime sent) {
        this.user = user;
        this.message = message;
        this.sent = sent;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSent() {
        return sent;
    }
}
