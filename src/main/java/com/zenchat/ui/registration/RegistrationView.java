package com.zenchat.ui.registration;

import com.zenchat.ui.FxView;

import java.net.URL;

public class RegistrationView extends FxView<RegistrationController> {

    public RegistrationView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/registration_view.fxml");
    }
}
