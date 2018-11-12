package com.zenchat.ui.app.registration;

import com.zenchat.ui.framework.component.FxController;
import com.zenchat.ui.framework.scene.Scenes;
import com.zenchat.ui.shared.loader.LoaderView;
import com.zenchat.ui.shared.notification.NotificationComponent;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

@Slf4j
public class RegistrationController extends Dialog implements FxController<RegistrationViewModel> {

    @FXML
    private StackPane stackPane;

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

    @FXML
    private Button registerButton;

    private LoaderView loaderView = new LoaderView();

    private RegistrationViewModel registrationViewModel;

    @Override
    public void initModel(RegistrationViewModel registrationModel) {
        this.registrationViewModel = registrationModel;

        ObservableList<String> errors = FXCollections.observableArrayList();
        errors.add("first.name");
        errors.add("last.name");
        errors.add("username");
        errors.add("password");

        // validate
        firstNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasErrors = !RegistrationValidator.validateName(firstNameTxt.getText());
            if(hasErrors) {
                errors.add("first.name");
            }
            else {
                errors.removeIf(key -> key.equals("first.name"));
            }
            firstNameLabel.setVisible(hasErrors);
        });

        lastNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasErrors = !RegistrationValidator.validateName(lastNameTxt.getText());
            if(hasErrors) {
                errors.add("last.name");
            }
            else {
                errors.removeIf(key -> key.equals("last.name"));
            }
            lastNameLabel.setVisible(hasErrors);
        });

        userNameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasErrors = !RegistrationValidator.validateUserName(userNameTxt.getText());
            if(hasErrors) {
                errors.add("username");
            }
            else {
                errors.removeIf(key -> key.equals("username"));
            }
            userNameLabel.setVisible(hasErrors);
        });

        passwordTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasErrors = !RegistrationValidator.validatePassword(passwordTxt.getText());
            if(hasErrors) {
                errors.add("password");
            }
            else {
                errors.removeIf(key -> key.equals("password"));
            }
            passwordLabel.setVisible(hasErrors);
        });

        registerButton.disableProperty().bind(Bindings.isNotEmpty(errors));

        registrationModel.onError((message) -> {
            Platform.runLater(() -> {
                stackPane.getChildren().remove(loaderView.getParent());

                NotificationComponent toastyComponent = new NotificationComponent(stackPane);
                toastyComponent.showError(message);
            });
        });

        registrationModel.onSuccess((message) -> {
            Platform.runLater(() -> {
                stackPane.getChildren().remove(loaderView.getParent());

                NotificationComponent toastyComponent = new NotificationComponent(stackPane);
                toastyComponent.onClose(() -> Scenes.changeScene("LoginComponent"));
                toastyComponent.showInformation("Your user has now been registered!");
            });
        });
    }

    @FXML
    public void onRegisterButtonClick(Event event) {
        String firstName = firstNameTxt.getText();
        String lastName = lastNameTxt.getText();
        String username = userNameTxt.getText();
        String password = passwordTxt.getText();

        stackPane.getChildren().add(loaderView.getParent());

        registrationViewModel.register(new RegistrationForm(firstName, lastName, username, password));
    }

    @FXML
    public void onCancelButtonClicked(Event event) {
        registrationViewModel.cancel();
    }
}
