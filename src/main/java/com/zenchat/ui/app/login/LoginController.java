package com.zenchat.ui.app.login;

import com.zenchat.ui.FxController;
import com.zenchat.ui.app.registration.RegistrationComponent;
import com.zenchat.ui.app.registration.RegistrationView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends Dialog implements FxController<LoginViewModel> {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordField;

    private LoginViewModel registrationViewModel;

    @Override
    public void initModel(LoginViewModel registrationModel) {
        this.registrationViewModel = registrationModel;
    }

    @FXML
    public void onCancelButtonClicked(ActionEvent event) {
        System.out.println("login canceled clicked");

    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {

        String username = userNameTextField.getText();
        String password = passwordField.getText();

        registrationViewModel.register(username, password);
    }

    @FXML
    public void onSignUpLinkClick(ActionEvent event) {
        Stage primaryStage = registrationViewModel.getPrimaryStage();

        RegistrationComponent registrationComponent = new RegistrationComponent(registrationViewModel.getClient());

        primaryStage.getScene().setRoot(new RegistrationView().getParent());
//        registrationComponent.show(primaryStage);

    }
}
