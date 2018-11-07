package com.zenchat.ui.shared.notification;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class NotificationController implements FxController<NotificationViewModel> {

    @FXML
    private Text errorText;

    private NotificationViewModel loginViewModel;

    @Override
    public void initModel(NotificationViewModel registrationModel) {
        this.loginViewModel = registrationModel;

        errorText.textProperty().bind(loginViewModel.getErrorMessage());
    }

    @FXML
    public void closeClicked(ActionEvent event) {
        loginViewModel.close();
    }
}
