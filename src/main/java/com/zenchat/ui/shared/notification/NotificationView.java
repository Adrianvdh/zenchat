package com.zenchat.ui.shared.notification;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

public class NotificationView {
    private Node parentNode;
    private NotificationController controller;

    public NotificationView() {
        init();
    }

    public void init() {

        try {
            FXMLLoader loader = new FXMLLoader(viewResource());
            this.parentNode = loader.load();
            this.controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected URL viewResource() {
        return getClass().getResource("/views/notification.fxml");
    }

    public Node getParent() {
        return parentNode;
    }

    public NotificationController getController() {
        return controller;
    }
}