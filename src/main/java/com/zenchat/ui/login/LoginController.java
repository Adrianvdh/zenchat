package com.zenchat.ui.login;

import com.zenchat.ui.FxController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends Dialog implements FxController<LoginViewModel> {

    @FXML
    private AnchorPane regPane;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    private LoginViewModel registrationViewModel;

    @Override
    public void initModel(LoginViewModel registrationModel) {
        this.registrationViewModel = registrationModel;

        regPane.requestFocus(); // Delegate the focus to container

        regPane.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("reg pane clicked");
            regPane.requestFocus();
        });
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {

    }

    @FXML
    public void onSignUpLinkClick(ActionEvent event) {

    }
}
