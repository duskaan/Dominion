package Handlers;

import Handlers.MessageHandler;

import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandlerFactory {

    //alle handler werden im Ordner gesucht und in einem set gespeichert zum durchiterieren beim getMsghandler von internet grundlage
    private static Set<Class> handlers = new HashSet<>();

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
    public static void createCommunicate(Socket clientSocket){
        MessageHandler handler = new MessageHandler(clientSocket);
        handler.openResources();

    }
}
