package com.zenchat.ui.app.serveraddress;

import com.zenchat.client.Client;
import com.zenchat.ui.app.login.LoginComponent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerComponent {

    private ServerAddressView serverAddressView = new ServerAddressView();
    private ServerAddressViewModel serverAddressViewModel;

    private Client client;

    public ServerComponent(Client client) {
        this.client = client;
    }

    public void show(Stage primaryStage) {
        ServerAddressController loginController = serverAddressView.getController();
        serverAddressViewModel = new ServerAddressViewModel(primaryStage);
        loginController.initModel(serverAddressViewModel);

        Scene regScene = new Scene(serverAddressView.getParent());

        LoginComponent loginComponent = new LoginComponent(client);

        serverAddressViewModel.onConnect((address) -> {
            System.out.println("Address " + address);

            loginComponent.show(primaryStage);
        });

        primaryStage.setTitle("Connect");
        primaryStage.setScene(regScene);
        primaryStage.show();
    }
}
