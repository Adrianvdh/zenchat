package com.zenchat.ui.shared.notification;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationViewModel {
    private ObjectProperty<NotificationMode> notificationModeObjectProperty = new SimpleObjectProperty<>();
    private StringProperty errorMessage = new SimpleStringProperty();
    private StringProperty informationMessage = new SimpleStringProperty();
    private Runnable onCloseHandler;

    public void onClose(Runnable handler) {
        this.onCloseHandler = handler;
    }

    public void close() {
        this.onCloseHandler.run();
    }

}
