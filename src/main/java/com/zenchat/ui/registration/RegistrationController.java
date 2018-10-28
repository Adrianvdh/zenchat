package com.zenchat.ui.registration;

import com.zenchat.ui.FxController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegistrationController extends Dialog implements FxController<RegistrationViewModel> {

    @FXML
    private AnchorPane regPane;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    private RegistrationViewModel registrationViewModel;

    @Override
    public void initModel(RegistrationViewModel registrationModel) {
        this.registrationViewModel = registrationModel;

        regPane.requestFocus(); // Delegate the focus to container

        regPane.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("reg pane clicked");
            regPane.requestFocus();
        });
    }

    @FXML
    public void onRegisterButtonClick(Event event) {

        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();

        registrationViewModel.register(usernameTextfield.getText(), passwordTextfield.getText());
    }
}
