package com.zenchat.ui.app.registration;

import java.util.function.BiConsumer;

public class RegistrationViewModel {

    private BiConsumer<String, String> onRegisterHandler;
    private Runnable onCancelHandler;
    private Runnable onSignUpHandler;

    public void register(String username, String password) {
        onRegisterHandler.accept(username, password);

    }

    public void cancel() {
        onCancelHandler.run();
    }

    public void onRegister(BiConsumer<String, String> handler) {
        this.onRegisterHandler = handler;
    }

    public void onCancel(Runnable handler) {
        this.onCancelHandler = handler;
    }
}
