package com.zenchat.ui.chat;

import com.zenchat.ui.FxController;
import com.zenchat.ui.chat.model.ChatMessage;
import com.zenchat.ui.chat.model.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static javafx.beans.binding.Bindings.createObjectBinding;

public class ChatController implements FxController<ChatViewModel> {

    @FXML
    private ListView<User> connectedUsersListView;

    @FXML
    private TextArea userChatHistoryTextArea;

    @FXML
    private TextField messageTextField;

    private ChatViewModel chatViewModel;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void initModel(ChatViewModel model) {
        this.chatViewModel = model;

        // bind connectedUsersListView
        ObservableList<User> users = model.getUsers();

        ListProperty<User> listProperty = new SimpleListProperty<>(users);
        connectedUsersListView.itemsProperty().bind(listProperty);
        connectedUsersListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (user != null) {
                    setText(String.format("%s [%s]", user.getName(), user.getStatus().name()));
                }
            }
        });

        connectedUsersListView.getFocusModel().focus(0);
        connectedUsersListView.getSelectionModel().select(0);

        connectedUsersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldUser, newUser) -> {
            String username = newUser.getName();
            chatViewModel.updateChatHistoryForUser(username);
        });

        // bind userChatHistoryTextArea
        ObservableList<ChatMessage> chatHistory = model.getChatHistory();
        ListProperty<ChatMessage> chatHistoryProperty = new SimpleListProperty<>(chatHistory);

        userChatHistoryTextArea.textProperty().bind(createObjectBinding(() -> formatChatHistory(chatHistoryProperty), chatHistoryProperty));
    }

    private String formatChatHistory(ListProperty<ChatMessage> chatHistory) {
        return chatHistory.stream()
                .map(chatMessage -> String.format("%s:\n%s", chatMessage.getUser(), chatMessage.getMessage()))
                .collect(Collectors.joining("\n\n"));
    }

    @FXML
    public void onSendButton(Event event) {
        chatViewModel.sendMessage(messageTextField.getText(), "josie");
    }


}
