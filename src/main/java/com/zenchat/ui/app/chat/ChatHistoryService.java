package com.zenchat.ui.app.chat;

import com.zenchat.ui.app.chat.model.ChatMessage;

import java.util.*;

public class ChatHistoryService {
    private Map<String, List<ChatMessage>> userChatHistory = new HashMap<>();

    public ChatHistoryService() {
        List<ChatMessage> chats = new ArrayList<>();
        chats.add(new ChatMessage("josie", "Hey adrian", null));

        userChatHistory.put("Adrian", chats);
    }

    public List<ChatMessage> getChatHistoryForUsername(String username) {
        if (!userChatHistory.containsKey(username)) {
            return Collections.emptyList();
        }

        return userChatHistory.get(username);
    }
}
