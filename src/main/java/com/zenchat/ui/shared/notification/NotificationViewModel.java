package com.zenchat.ui.shared.notification;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class NotificationViewModel {
    private StringProperty errorMessage = new SimpleStringProperty();
    private Runnable onCloseHandler;

    public void onClose(Runnable handler) {
        this.onCloseHandler = handler;
    }

    public void close() {
        this.onCloseHandler.run();
    }
}
