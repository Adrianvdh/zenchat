package com.zenchat.ui.framework.component;

public interface Component {
    void onInit();

    String title();

    FxView fxView();
}
