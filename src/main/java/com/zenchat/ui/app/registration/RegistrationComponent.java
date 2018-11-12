package com.zenchat.ui.app.registration;

import com.zenchat.client.Client;
import com.zenchat.ui.framework.component.Component;
import com.zenchat.ui.framework.component.FxView;
import com.zenchat.ui.framework.concurrent.AsyncUtil;
import com.zenchat.ui.framework.scene.Scenes;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationComponent implements Component {

    private RegistrationView registrationView = new RegistrationView();
    private RegistrationViewModel registrationModel = new RegistrationViewModel();
    private RegistrationService registrationService;

    public RegistrationComponent(Client client) {
        registrationService = new RegistrationService(client);
    }

    @Override
    public void onInit() {
        RegistrationController registrationViewController = registrationView.getController();
        registrationViewController.initModel(registrationModel);

        registrationModel.onRegister((registrationForm) -> {

            registrationService.onError(exception -> {
                log.error("Failed to register user {}", exception.getMessage());
                registrationModel.showError(exception.getMessage());
            });

            registrationService.onSuccess(userRegisterResponse -> {
                registrationModel.showSuccess("User registered!");

                log.info("Registered user {}", userRegisterResponse.getUsername());
                Scenes.changeScene("LoginComponent");
            });


            AsyncUtil.runTask(new Task<Void>() {
                @Override
                protected Void call() {
                    registrationService.registerUser(registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getUsername(), registrationForm.getPassword());
                    return null;
                }
            });

        });


        registrationModel.onCancel(() -> {
            Scenes.changeScene("LoginComponent");
        });
    }

    @Override
    public String title() {
        return "ZenChat | Registration";
    }

    @Override
    public FxView fxView() {
        return registrationView;
    }
}
