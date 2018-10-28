package com.zenchat;

import com.zenchat.client.Client;
import com.zenchat.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientServerTest {

    private static String HOST = "localhost";
    private static int PORT = 34567;

    private Server server;

    @Before
    public void setUp() {
        server = new Server(PORT);
        server.start();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testConnectDisconnect() {
        Client client = new Client(HOST, PORT);
        client.connect();
        client.disconnect();
    }


    @Test
    public void testOnConnect_clientConnects() {
        Client client = new Client(HOST, PORT);

        client.onConnect((ack) -> {
            System.out.println("Acknowledged onConnect : InitId: " + ack.getIdentifier());
        });

        client.connect();
        client.disconnect();
        System.out.println("Disconnect");
    }

}
