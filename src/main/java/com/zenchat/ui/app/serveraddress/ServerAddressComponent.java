package com.zenchat.ui.app.serveraddress;

import com.zenchat.client.Client;
import com.zenchat.ui.app.login.LoginComponent;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import com.zenchat.ui.framework.scene.Scenes;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerAddressComponent implements Component {

    private ServerAddressView serverAddressView = new ServerAddressView();
    private ServerAddressViewModel serverAddressViewModel;

    private Client client;

    public ServerAddressComponent(Client client) {
        this.client = client;
    }

    @Override
    public void show(Stage primaryStage) {
        primaryStage.setTitle("Connect");

        ServerAddressController loginController = serverAddressView.getController();
        serverAddressViewModel = new ServerAddressViewModel(primaryStage);
        loginController.initModel(serverAddressViewModel);

        LoginComponent loginComponent = new LoginComponent(client);

        serverAddressViewModel.onConnect((address) -> {
            System.out.println("Address " + address);

            client.connect(address, 33120);
            loginComponent.show(primaryStage);

            Scenes.changeScene("LoginComponent");
        });

    }

    @Override
    public FxView fxView() {
        return serverAddressView;
    }
}
