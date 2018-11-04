package com.zenchat.ui.app.login;

import com.zenchat.client.Client;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginComponent {

    private LoginView loginView = new LoginView();
    private LoginViewModel loginViewModel;
    private LoginService loginService;

    private Client client;

    public LoginComponent(Client client) {
        this.client = client;
        loginService = new LoginService(client);
    }

    public void show(Stage stage) {
        LoginController loginController = loginView.getController();
        loginViewModel = new LoginViewModel(stage);
        loginController.initModel(loginViewModel);

        Parent parent = loginView.getParent();
        Scene loginScene = new Scene(loginView.getParent());
        loginViewModel.onLogin((username, password) -> {
            System.out.println("Login " + username);
            loginService.loginUser(username, password);

        });

        stage.setTitle("Login");
        stage.setScene(loginScene);
    }
}
