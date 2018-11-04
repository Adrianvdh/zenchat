package com.zenchat.ui.login;

import com.zenchat.ui.FxView;

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
