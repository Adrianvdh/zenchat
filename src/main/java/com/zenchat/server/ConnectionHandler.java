package com.zenchat.server;

import com.zenchat.common.messaging.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler {
    private Socket socket;

    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    private MessageHandler messageHandler;

    public ConnectionHandler(Socket clientSocket) {
        this.socket = clientSocket;
        trySetupInAndOutStreams(clientSocket);
    }

    private void trySetupInAndOutStreams(Socket clientSocket) {

        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle() {
        this.messageHandler = new MessageHandler(objectOutputStream);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = (Message) objectInputStream.readObject();
                this.messageHandler.handle(message);
            } catch (EOFException e) {
                // DataStream's detect end-of-life condition by throwing EOFException
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        tryClose();
    }

    private void tryClose() {
        try {
            this.objectOutputStream.close();
            this.objectInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
