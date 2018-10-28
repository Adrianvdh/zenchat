package com.zenchat.ui.chat;

import com.zenchat.ui.chat.model.ChatMessage;
import com.zenchat.ui.chat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Consumer;

public class ChatViewModel {
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<ChatMessage> chatHistory = FXCollections.observableArrayList();

    private Consumer<ChatMessage> chatMessageConsumer;
    private ChatHistoryService chatHistoryService;

    public ChatViewModel(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public ObservableList<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ObservableList<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(String messageTextFieldText, String recipient) {
        this.chatMessageConsumer.accept(new ChatMessage(recipient, messageTextFieldText, null));
    }

    public void onSendMessage(Consumer<ChatMessage> chatMessageConsumer) {
        this.chatMessageConsumer = chatMessageConsumer;
    }

    public void updateChatHistoryForUser(String username) {
        chatHistory.clear();
        List<ChatMessage> chats = chatHistoryService.getChatHistoryForUsername(username);

        for (ChatMessage chatMessage : chats) {
            chatHistory.add(chatMessage);
        }

    }
}
