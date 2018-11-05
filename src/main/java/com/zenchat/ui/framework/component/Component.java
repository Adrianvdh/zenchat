package com.zenchat.ui.framework.component;

import javafx.stage.Stage;

public interface Component {

    void show(Stage stage);

    FxView fxView();
}
