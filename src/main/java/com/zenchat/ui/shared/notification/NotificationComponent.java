package com.zenchat.ui.shared.notification;

import javafx.scene.layout.Pane;

public class NotificationComponent {
    private Pane parentPane;

    private NotificationView notificationView = new NotificationView();
    private NotificationViewModel notificationViewModel;
    private NotificationController notificationController;
    private Runnable onCloseHandler;

    public NotificationComponent(Pane node) {
        this.parentPane = node;

        notificationViewModel = new NotificationViewModel();
        notificationController = notificationView.getController();


        notificationController.initModel(notificationViewModel);
        notificationController.setView(notificationView);
    }

    public void showError(String message) {
        notificationViewModel.getErrorMessage().setValue(message);
        notificationViewModel.getNotificationModeObjectProperty().set(NotificationMode.ERROR);
        parentPane.getChildren().add(notificationView.getParent());

        notificationViewModel.onClose(() -> {
            parentPane.getChildren().remove(notificationView.getParent());
            if (onCloseHandler != null) {
                this.onCloseHandler.run();
            }
        });
    }

    public void showInformation(String message) {
        notificationViewModel.getInformationMessage().setValue(message);
        notificationViewModel.getNotificationModeObjectProperty().set(NotificationMode.INFORMMATION);

        parentPane.getChildren().add(notificationView.getParent());

        notificationViewModel.onClose(() -> {
            parentPane.getChildren().remove(notificationView.getParent());
            if (onCloseHandler != null) {
                this.onCloseHandler.run();
            }
        });
    }

    public void onClose(Runnable handler) {
        this.onCloseHandler = handler;
    }
}
