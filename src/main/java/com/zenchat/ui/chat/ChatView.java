package com.zenchat.ui.chat;

import com.zenchat.ui.FxView;

import java.net.URL;

public class ChatView extends FxView<ChatController> {

    public ChatView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/chat_view.fxml");
    }
}
