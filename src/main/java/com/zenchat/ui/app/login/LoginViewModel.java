package com.zenchat.ui.app.login;

import com.zenchat.client.Client;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.function.BiConsumer;

@Getter
public class LoginViewModel {
    private Client client;
    private Stage primaryStage;
    private BiConsumer<String, String> onRegisterHandler;

    public LoginViewModel(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void register(String username, String password) {
        onRegisterHandler.accept(username, password);

    }

    public void onLogin(BiConsumer<String, String> handler) {
        this.onRegisterHandler = handler;
    }
}
