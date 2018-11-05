package com.zenchat.ui.app.registration;

import com.zenchat.ui.framework.component.FxView;

import java.net.URL;

public class RegistrationView extends FxView<RegistrationController> {

    public RegistrationView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/views/registration_view.fxml");
    }
}
