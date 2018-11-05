package com.zenchat.ui.app.serveraddress;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class ServerAddressViewModel {
    private Consumer<String> onHandler;

    public void connect(String address) {
        onHandler.accept(address);
    }

    public void onConnect(Consumer<String> handler) {
        this.onHandler = handler;
    }
}
