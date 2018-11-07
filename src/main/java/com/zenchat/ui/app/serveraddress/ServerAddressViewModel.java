package com.zenchat.ui.app.serveraddress;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
public class ServerAddressViewModel {

    private BooleanProperty isConnected = new SimpleBooleanProperty(false);

    private Consumer<String> onHandler;
    private Consumer<String> onError;

    public void connect(String address) {
        onHandler.accept(address);
    }

    public void onConnect(Consumer<String> handler) {
        this.onHandler = handler;
    }

    public void onError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void showError(String message) {
        this.onError.accept(message);
    }
}
