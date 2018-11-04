package com.zenchat.ui.registration;

import com.zenchat.client.Client;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationComponent {

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


        Scene regScene = new Scene(registrationView.getParent(),  382, 510);
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);

        registrationModel.onRegister((username, password) -> {
            System.out.println("Registering " + username);

            registrationService.registerUser(username, password);

            stage.hide();
        });

        stage.setTitle("Register");
        stage.setScene(regScene);
        stage.showAndWait();
    }
}
