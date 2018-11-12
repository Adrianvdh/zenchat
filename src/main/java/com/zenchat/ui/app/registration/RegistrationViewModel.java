package com.zenchat.ui.app.registration;

import java.util.function.Consumer;

public class RegistrationViewModel {

    private Consumer<RegistrationForm> onRegisterHandler;
    private Runnable onCancelHandler;
    private Consumer<String> onError;
    private Consumer<String> onSuccess;

    public void register(RegistrationForm registrationForm) {
        onRegisterHandler.accept(registrationForm);
    }

    public void cancel() {
        onCancelHandler.run();
    }

    public void onRegister(Consumer<RegistrationForm> handler) {
        this.onRegisterHandler = handler;
    }

    public void onCancel(Runnable handler) {
        this.onCancelHandler = handler;
    }

    public void onError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void showError(String message) {
        this.onError.accept(message);
    }

    public void onSuccess(Consumer<String> onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void showSuccess(String message) {
        this.onSuccess.accept(message);
    }

}
