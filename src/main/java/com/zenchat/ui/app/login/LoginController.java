package com.zenchat.ui.app.login;

import com.zenchat.ui.framework.component.FxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController implements FxController<LoginViewModel> {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordField;

    private LoginViewModel loginViewModel;

    @Override
    public void initModel(LoginViewModel registrationModel) {
        this.loginViewModel = registrationModel;
    }

    @FXML
    public void onCancelButtonClicked(ActionEvent event) {
        loginViewModel.cancel();
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {
        loginViewModel.login(userNameTextField.getText(), passwordField.getText());
    }

    @FXML
    public void onSignUpLinkClick(ActionEvent event) {
        loginViewModel.signUp();
    }
}
