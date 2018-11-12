package com.zenchat.ui.app.serveraddress;

import com.zenchat.ui.framework.component.FxController;
import com.zenchat.ui.framework.scene.Scenes;
import com.zenchat.ui.shared.loader.LoaderView;
import com.zenchat.ui.shared.notification.NotificationComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ServerAddressController extends Dialog implements FxController<ServerAddressViewModel> {

    @FXML
    public ImageView logoImageView;

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane connectionPane;

    @FXML
    private TextField serverAddressTxt;

    @FXML
    private Label serverAddressLabel;

    private ServerAddressViewModel serverAddressViewModel;

    @Override
    public void initModel(ServerAddressViewModel serverAddressViewModel) {
        this.serverAddressViewModel = serverAddressViewModel;
    }

    @FXML
    public void onConnectButtonClicked(ActionEvent event) {
        serverAddressTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            serverAddressLabel.setVisible(newValue.isEmpty());
        });

        if(serverAddressTxt.getText().isEmpty()) {
            serverAddressLabel.setVisible(true);
            return;
        }

        disable();

        LoaderView loaderView = new LoaderView();
        stackPane.getChildren().add(loaderView.getParent());

        serverAddressViewModel.onError((message) -> {
            stackPane.getChildren().remove(loaderView.getParent());

            NotificationComponent toastyComponent = new NotificationComponent(stackPane);
            toastyComponent.onClose(this::enable);
            toastyComponent.showError(message);
        });

        serverAddressViewModel.getIsConnected().addListener((observable, oldValue, newValue) -> {
            System.out.println("Connected " + newValue);
            if(newValue) {
                stackPane.getChildren().remove(loaderView.getParent());
                enable();
            }
            Scenes.changeScene("LoginComponent");
        });

        serverAddressViewModel.connect(serverAddressTxt.getText());
    }

    private void disable() {
        connectionPane.setDisable(true);
        logoImageView.setImage(new Image("/images/zen-chat-disabled-icon.png"));
    }

    private void enable() {

        connectionPane.setDisable(false);
        logoImageView.setImage(new Image("/images/zen-chat-icon.png"));
    }
}
