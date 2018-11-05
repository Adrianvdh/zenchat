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
    public void onInit() {
        ServerAddressController loginController = serverAddressView.getController();
        serverAddressViewModel = new ServerAddressViewModel();
        loginController.initModel(serverAddressViewModel);

        serverAddressViewModel.onConnect((address) -> {
            System.out.println("Address " + address);

            client.connect(address, 33120);

            Scenes.changeScene("LoginComponent");
        });

    }

    @Override
    public String title() {
        return "ZenChat | Connect";
    }

    @Override
    public FxView fxView() {
        return serverAddressView;
    }
}
