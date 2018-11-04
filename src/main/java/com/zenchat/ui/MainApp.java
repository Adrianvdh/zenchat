package com.zenchat.ui;

import com.zenchat.client.Client;
import com.zenchat.ui.app.chat.ChatComponent;
import com.zenchat.ui.app.serveraddress.ServerComponent;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Client client = new Client("localhost", 33120);

        primaryStage.setHeight(600);
        primaryStage.setWidth(900);

        ServerComponent serverComponent = new ServerComponent(client);
        serverComponent.show(primaryStage);
    }

    @Override
    public void stop() throws Exception {
//        System.exit(0);
    }
}
