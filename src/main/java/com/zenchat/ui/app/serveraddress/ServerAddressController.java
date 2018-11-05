package com.zenchat.ui.app.serveraddress;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class ServerAddressController extends Dialog implements FxController<ServerAddressViewModel> {

    @FXML
    private TextField serverAddressTextField;

    private ServerAddressViewModel serverAddressViewModel;

    @Override
    public void initModel(ServerAddressViewModel serverAddressViewModel) {
        this.serverAddressViewModel = serverAddressViewModel;
    }

    @FXML
    public void onConnectButtonClicked(ActionEvent event) {
        serverAddressViewModel.connect(serverAddressTextField.getText());
    }
}
