package com.zenchat.ui.app.chat;

import com.zenchat.client.Client;
import com.zenchat.ui.app.chat.model.User;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatComponent implements Component {

    private ChatView chatView = new ChatView();
    private ChatViewModel chatViewModel;
    private ChatHistoryService chatHistoryService;

    private Client client;

    public ChatComponent(Client client) {
        this.client = client;

        chatHistoryService = new ChatHistoryService();
        chatViewModel = new ChatViewModel(chatHistoryService);
    }

    @Override
    public void show(Stage primaryStage) {
        ChatController chatViewController = chatView.getController();

        chatViewController.initModel(chatViewModel);
        chatViewModel.onSendMessage(chatMessage -> {
            System.out.println("Recieved message");
        });


        primaryStage.setTitle("ZenChat");

        chatViewModel.addUser(new User("Adrian", User.Status.ONLINE));
        chatViewModel.addUser(new User("Josie", User.Status.ONLINE));
    }

    @Override
    public FxView fxView() {
        return chatView;
    }
}
