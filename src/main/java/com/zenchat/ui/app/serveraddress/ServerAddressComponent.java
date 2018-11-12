package com.zenchat.ui.app.serveraddress;

import com.zenchat.client.Client;
import com.zenchat.client.ClientException;
import com.zenchat.common.message.AckMessage;
import com.zenchat.ui.app.login.LoginComponent;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import com.zenchat.ui.framework.scene.Scenes;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
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
            client.onConnect(ackMessage -> {
                serverAddressViewModel.getIsConnected().setValue(true);
            });

            serverAddressViewModel.getIsConnected().set(false);
            try {
                client.connect(address, 33120);
            } catch (ClientException e) {
                serverAddressViewModel.showError(e.getMessage());
                log.error("Connection error {}", e.getMessage());
                return;
            }
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
