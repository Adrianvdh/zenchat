package com.zenchat.ui.app.chat;

import com.zenchat.ui.framework.component.FxView;

import java.net.URL;

public class ChatView extends FxView<ChatController> {

    public ChatView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/views/chat_view.fxml");
    }
}
