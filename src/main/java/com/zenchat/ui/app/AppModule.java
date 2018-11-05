package com.zenchat.ui.app;

import com.zenchat.client.Client;
import com.zenchat.ui.app.chat.ChatComponent;
import com.zenchat.ui.app.login.LoginComponent;
import com.zenchat.ui.app.registration.RegistrationComponent;
import com.zenchat.ui.app.serveraddress.ServerAddressComponent;
import com.zenchat.ui.framework.component.ComponentProvider;
import com.zenchat.ui.framework.component.ComponentRegistry;

public class AppModule {

    public static ComponentRegistry componentRegistry() {
        Client client = new Client();

        return ComponentRegistry.fromComponentProviders(
                new ComponentProvider("ChatComponent", new ChatComponent(client)),
                new ComponentProvider("LoginComponent", new LoginComponent(client)),
                new ComponentProvider("RegistrationComponent", new RegistrationComponent(client)),
                new ComponentProvider("ServerAddressComponent", new ServerAddressComponent(client))
        );
    }
}
