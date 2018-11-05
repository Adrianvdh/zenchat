package com.zenchat.ui.app.login;

import com.zenchat.ui.framework.component.FxView;

import java.net.URL;

public class LoginView extends FxView<LoginController> {

    public LoginView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/views/login_view.fxml");
    }
}
