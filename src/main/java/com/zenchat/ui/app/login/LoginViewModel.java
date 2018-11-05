package com.zenchat.ui.app.login;

import lombok.Getter;

import java.util.function.BiConsumer;

@Getter
public class LoginViewModel {
    private BiConsumer<String, String> onRegisterHandler;
    private Runnable onCancelHandler;
    private Runnable onSignUpHandler;

    public void onLogin(BiConsumer<String, String> handler) {
        this.onRegisterHandler = handler;
    }

    public void onCancel(Runnable handler) {
        this.onCancelHandler = handler;
    }

    public void onSignUp(Runnable handler) {
        this.onSignUpHandler = handler;
    }

    public void login(String username, String password) {
        onRegisterHandler.accept(username, password);
    }

    public void cancel() {
        this.onCancelHandler.run();
    }

    public void signUp() {
        this.onSignUpHandler.run();
    }
}
