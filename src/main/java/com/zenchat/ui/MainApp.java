package com.zenchat.ui;

import com.zenchat.client.Client;
import com.zenchat.ui.chat.ChatComponent;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Client client = new Client("localhost", 33120);
        client.connect();

        ChatComponent chatModule = new ChatComponent(client);
        chatModule.show(primaryStage);
    }

    @Override
    public void stop() throws Exception {
//        System.exit(0);
    }
}
