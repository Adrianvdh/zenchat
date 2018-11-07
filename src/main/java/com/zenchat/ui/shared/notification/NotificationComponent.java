package com.zenchat.ui.shared.notification;

import javafx.scene.layout.Pane;

public class NotificationComponent {

    private Pane parentPane;

    private Runnable onCloseHandler;
    private NotificationView toastyView = new NotificationView();
    private NotificationViewModel toastyViewModel;

    public NotificationComponent(Pane node) {
        this.parentPane = node;
    }

    public void show(String message) {
        NotificationController loginController = toastyView.getController();
        toastyViewModel = new NotificationViewModel();

        toastyViewModel.getErrorMessage().setValue(message);
        loginController.initModel(toastyViewModel);

        parentPane.getChildren().add(toastyView.getParent());
        toastyViewModel.onClose(() -> {
            parentPane.getChildren().remove(toastyView.getParent());
            this.onCloseHandler.run();
        });
    }

    public void onClose(Runnable handler) {
        this.onCloseHandler = handler;
    }
}
