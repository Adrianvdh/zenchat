package com.zenchat.ui.app.registration;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegistrationController extends Dialog implements FxController<RegistrationViewModel> {

    @FXML
    private AnchorPane regPane;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordField;

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

        String firstName = firstNameTextField.getText();
        String lastName  = lastNameTextField.getText();
        String username = userNameTextField.getText();
        String password = passwordField.getText();

        registrationViewModel.register(username, password);
    }

    @FXML
    public void onCancelButtonClicked(Event event) {
        registrationViewModel.cancel();
    }
}
