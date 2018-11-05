package com.zenchat.ui.framework.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public abstract class FxView<T> {

    private T controller;
    private Parent parentNode;

    public FxView() {
        try {
            FXMLLoader loader = new FXMLLoader(viewResource());
            this.parentNode = loader.load();
            this.controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T getController() {
        return controller;
    }

    public Parent getParent() {
        return parentNode;
    }

    protected abstract URL viewResource();
}
