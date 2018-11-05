package com.zenchat.ui.app.login;

import com.zenchat.client.Client;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import com.zenchat.ui.framework.scene.Scenes;
import javafx.stage.Stage;

public class LoginComponent implements Component {

    private LoginView loginView = new LoginView();
    private LoginViewModel loginViewModel;
    private LoginService loginService;
    private Client client;

    public LoginComponent(Client client) {
        this.client = client;
        loginService = new LoginService(client);
    }

    @Override
    public void show(Stage stage) {
        LoginController loginController = loginView.getController();

        loginViewModel = new LoginViewModel(stage);
        loginController.initModel(loginViewModel);

        stage.setTitle("Login");

        loginViewModel.onLogin((username, password) -> {
            System.out.println("Login " + username);
//            loginService.loginUser(username, password);

            Scenes.changeScene("ChatComponent");
        });

        loginViewModel.onCancel(() -> {
            Scenes.changeScene("ServerAddressComponent");
        });

        loginViewModel.onSignUp(() -> {
            Scenes.changeScene("RegistrationComponent");
        });
    }

    @Override
    public FxView fxView() {
        return loginView;
    }
}
