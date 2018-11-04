package com.zenchat.ui.login;

import java.util.function.BiConsumer;

public class LoginViewModel {

    private BiConsumer<String, String> onRegisterHandler;

    public void register(String username, String password) {
        onRegisterHandler.accept(username, password);

    }

    public void onLogin(BiConsumer<String, String> handler) {
        this.onRegisterHandler = handler;
    }

}
