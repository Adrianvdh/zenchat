package com.zenchat.ui.app.registration;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegistrationController extends Dialog implements FxController<RegistrationViewModel> {

    @FXML
    private TextField firstNameTxt;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField userNameTxt;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label passwordLabel;

    private RegistrationViewModel registrationViewModel;

    @Override
    public void initModel(RegistrationViewModel registrationModel) {
        this.registrationViewModel = registrationModel;

        firstNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            firstNameLabel.setVisible("invalid".equals(newValue));
        });

        lastNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            lastNameLabel.setVisible("invalid".equals(newValue));
        });

        userNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            userNameLabel.setVisible("invalid".equals(newValue));
        });

        passwordTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordLabel.setVisible("invalid".equals(newValue));
        });


    }

    @FXML
    public void onRegisterButtonClick(Event event) {

        String firstName = firstNameTxt.getText();
        String lastName = lastNameTxt.getText();
        String username = userNameTxt.getText();
        String password = passwordTxt.getText();

        registrationViewModel.register(username, password);
    }

    @FXML
    public void onCancelButtonClicked(Event event) {
        registrationViewModel.cancel();
    }
}
