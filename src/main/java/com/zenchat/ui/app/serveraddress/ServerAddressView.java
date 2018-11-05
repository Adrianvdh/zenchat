package com.zenchat.ui.app.serveraddress;

import com.zenchat.ui.framework.component.FxView;

import java.net.URL;

public class ServerAddressView extends FxView<ServerAddressController> {

    public ServerAddressView() {
        super();
    }

    @Override
    protected URL viewResource() {
        return getClass().getResource("/views/server_view.fxml");
    }
}
