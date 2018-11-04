package com.zenchat.ui.app.serveraddress;

import com.zenchat.client.Client;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class ServerAddressViewModel {
    private Client client;
    private Stage primaryStage;
    private Consumer<String> onHandler;

    public ServerAddressViewModel(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void connect(String address) {
        onHandler.accept(address);

    }

    public void onConnect(Consumer<String> handler) {
        this.onHandler = handler;
    }
}
