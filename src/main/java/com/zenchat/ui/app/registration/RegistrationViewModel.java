package com.zenchat.ui.app.registration;

import java.util.function.BiConsumer;

public class RegistrationViewModel {

    private BiConsumer<String, String> onRegisterHandler;

    public void register(String username, String password) {
        onRegisterHandler.accept(username, password);

    }

    public void onRegister(BiConsumer<String, String> handler) {
        this.onRegisterHandler = handler;
    }

}
