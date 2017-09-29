package Handlers;

import Models.WriteOtherClients;
import Server.LogHandling;

import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandlerFactory {

    //alle handler werden im Ordner gesucht und in einem set gespeichert zum durchiterieren beim getMsghandler von internet grundlage
    private static Set<Class> handlers = new HashSet<>();
    private static Map<Socket, String> players;

    public static void addHandler(String handlerName) {
        Class handlerClass;

        try {
            handlerClass = Class.forName(handlerName);
            handlers.add(handlerClass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* asks all the added messageHandlers if they are the meant constructor, if they are, they send out a certain
    exception which shows getMessageHandler, that this is the handler its looking for */
    public static MessageHandler getMessageHandler(String message) {
        MessageHandler messageHandler = null;
        for (Class handler : handlers) {
            if (messageHandler == null) {
                try {
                    Constructor constructor = handler.getConstructor(String.class);
                    try {
                        messageHandler = (MessageHandler) constructor.newInstance(message);
                    } catch (Exception e) {
                        messageHandler = null;
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return messageHandler;
    }

    public static void createCommunicate(Socket clientSocket) {
        LogHandling.logOnFile(Level.INFO, "Communication is created");
        System.out.println("Communication is created "+clientSocket);
        //LogHandling.closeResources();
        MessageHandler handler = new MessageHandler(clientSocket);

        handler.setWriteOtherClients(handler); //todo does this make sense?
        handler.getWriteOtherClients().addWaitingClient();
        handler.openResources();
        handler.read();

    }
}
