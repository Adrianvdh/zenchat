package com.zenchat.ui.login;

import com.zenchat.client.Client;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginComponent {

    private LoginView loginView = new LoginView();
    private LoginViewModel loginViewModel = new LoginViewModel();
    private LoginService loginService;

    private Client client;

    public LoginComponent(Client client) {
        this.client = client;
        loginService = new LoginService(client);
    }

    public void show(Stage primaryStage) {
        LoginController loginController = loginView.getController();
        loginController.initModel(loginViewModel);


        Scene regScene = new Scene(loginView.getParent(),  382, 418);
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        loginViewModel.onLogin((username, password) -> {
            System.out.println("Registering " + username);

            loginService.loginUser(username, password);

            stage.hide();
        });

        stage.setTitle("Login");
        stage.setScene(regScene);
        stage.showAndWait();
    }
}
