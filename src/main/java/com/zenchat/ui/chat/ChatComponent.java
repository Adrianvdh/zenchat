package com.zenchat.ui.chat;

import com.zenchat.client.Client;
import com.zenchat.ui.chat.model.User;
import com.zenchat.ui.login.LoginComponent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatComponent {

    private ChatView chatView = new ChatView();
    private ChatViewModel chatViewModel;
    private ChatHistoryService chatHistoryService;

    private Client client;

    public ChatComponent(Client client) {
        this.client = client;

        chatHistoryService = new ChatHistoryService();
        chatViewModel = new ChatViewModel(chatHistoryService);
    }

    public void show(Stage primaryStage) {
        ChatController chatViewController = chatView.getController();

        chatViewController.initModel(chatViewModel);
        chatViewModel.onSendMessage(chatMessage -> {
            System.out.println("Recieved message");
        });

        Scene scene = new Scene(chatView.getParent(), 800, 550);
        primaryStage.setTitle("ZenChat");
        primaryStage.setScene(scene);
        primaryStage.show();

        LoginComponent loginComponent = new LoginComponent(client);
        loginComponent.show(primaryStage);

        chatViewModel.addUser(new User("Adrian", User.Status.ONLINE));
        chatViewModel.addUser(new User("Josie", User.Status.ONLINE));


    }
}
