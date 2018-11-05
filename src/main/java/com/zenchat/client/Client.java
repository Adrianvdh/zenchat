package com.zenchat.client;

import com.zenchat.common.message.AckMessage;
import com.zenchat.common.message.Message;
import com.zenchat.common.message.protocol.Initialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.zenchat.common.message.HeadersProperties.SESSION_ID;

public class Client {
    private Logger logger = LoggerFactory.getLogger(Client.class);

    private String hostname;
    private int port;

    private Socket socketConnection;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Consumer<AckMessage> onConnect;

    private List<String> unAcknowledgedMessageIds = new ArrayList<>();
    private String sessionId;

    public Client() {}

    /**
     * try connect the client to the server using the host and port specified in the constructor.
     * <p>
     * If the onConnect callback is registered before calling this method, it will be invoked
     * once a connection has been established with the server.
     * <p>
     * This method blocks while waiting for a server acknowledgement.
     */
    public void connect(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;

        logger.info("Client connecting to server {}:{}", hostname, port);
        if (socketConnection == null) {
            trySetupInAndOutStreams();

            Message<Initialize> initializeMessage = new Message<>(new Initialize());
            Future<Message<AckMessage>> ackMessageFuture = send(initializeMessage, throwable -> {
                throw new RuntimeException(throwable);
            });
            unAcknowledgedMessageIds.add(initializeMessage.getIdentifier());

            Message<AckMessage> ackMessage = null;
            try {
                ackMessage = ackMessageFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            String initializeMessageId = unAcknowledgedMessageIds.get(0);
            String ackId = ackMessage.getPayload().getIdentifier();

            if (initializeMessageId.equals(ackId) && onConnect != null) {
                onConnect.accept(ackMessage.getPayload());
            }
            unAcknowledgedMessageIds.remove(ackId);

            if(!ackMessage.getHeaders().containsHeader(SESSION_ID)) {
                throw new ClientException("Server didn't respond with a sessionId");
            }
            this.sessionId = ackMessage.getHeaders().get(SESSION_ID);

            logger.info("Client connected.");
        } else {
            throw new ClientException("Cannot connect client that is already connected!");
        }
    }

    public <T, R> Future<Message<R>> send(Message<T> message, ErrorCallback errorCallback) {
        apply(message);

        unAcknowledgedMessageIds.add(message.getIdentifier());

        return CompletableFuture
                .supplyAsync(() -> {
                    Message<R> responseMessage;
                    try {
                        responseMessage = waitForServerResponse();
                    } catch (IOException e) {
                        throw new RuntimeException("An I/O error occurred between the client and server!", e);
                    }

                    unAcknowledgedMessageIds.remove(responseMessage.getCorrelationId());

                    if(Throwable.class.isAssignableFrom(responseMessage.getPayloadType())) {
                        throw new RuntimeException((Throwable) responseMessage.getPayload());
                    }

                    return responseMessage;
                }, executorService)
                .handle((tMessage, throwable) -> {
                    if(throwable != null && errorCallback != null) {
                        errorCallback.onError(throwable.getCause());
                    }

                    return tMessage;
                });
    }

    private <T> Message<T> waitForServerResponse() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            Message<T> message = null;
            try {
                message = (Message) inputStream.readObject();
                if (message == null) {
                    break;
                }
            } catch (EOFException e) {
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return message;
        }
        return null;
    }


    /**
     * Registers an consumer that is called when the client has connected to the server.
     * This method blocks until the server responds.
     */
    public void onConnect(Consumer<AckMessage> onConnect) {
        this.onConnect = onConnect;
    }

    private void apply(Message<?> message) {
        try {
            this.outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void trySetupInAndOutStreams() {
        try {
            this.socketConnection = new Socket(hostname, port);
            this.outputStream = new ObjectOutputStream(socketConnection.getOutputStream());
            this.inputStream = new ObjectInputStream(socketConnection.getInputStream());
        } catch (ConnectException e) {
            throw new ClientException(String.format("An error occurred when connecting to the server! %s", e.getMessage()));
        } catch (IOException e) {
            throw new ClientException(String.format("An error occurred! %s", e.getMessage()));
        }
    }

    /**
     * Ends the network session with the server
     */
    public void disconnect() {
        logger.info("Client trying to disconnect from server");
        if (socketConnection == null) {
            logger.warn("Could not disconnect because the client isn't connected yet!");
            return;
        }

        // send disconnect

        try {
            this.outputStream.close();
            this.inputStream.close();
            this.socketConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketConnection = null;
    }
}
