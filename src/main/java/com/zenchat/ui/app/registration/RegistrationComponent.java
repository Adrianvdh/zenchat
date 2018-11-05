package com.zenchat.ui.app.registration;

import com.zenchat.client.Client;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import com.zenchat.ui.framework.scene.Scenes;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistrationComponent implements Component {

    private RegistrationView registrationView = new RegistrationView();
    private RegistrationViewModel registrationModel = new RegistrationViewModel();
    private RegistrationService registrationService;

    private Client client;

    public RegistrationComponent(Client client) {
        this.client = client;
        registrationService = new RegistrationService(client);
    }

    public void show(Stage primaryStage) {
        RegistrationController registrationViewController = registrationView.getController();
        registrationViewController.initModel(registrationModel);


        registrationModel.onRegister((username, password) -> {
            registrationService.registerUser(username, password);

            Scenes.changeScene("LoginComponent");
        });

        registrationModel.onCancel(() -> {
            Scenes.changeScene("LoginComponent");
        });

        primaryStage.setTitle("Register");
    }

    @Override
    public FxView fxView() {
        return registrationView;
    }
}
