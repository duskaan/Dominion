package Handlers;


import Server.LogHandling;
import sun.rmi.runtime.Log;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.SERVER.toString();
    public static ArrayList<TempGame> tempGameArrayList = new ArrayList<>();
    private ServerLoginMessageHandler observedLoginMessageHandler;
    private ServerRegisterMessageHandler observedRegisterMessageHandler;
    private MessageHandler superHandler;

    public ServerMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAIN_HANDLER_INDEX);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public ServerMessageHandler() {
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;

        String subHandler = splitMessage(msgIn, SUB_HANDLER_INDEX);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        handler.handleMessage(msgIn, this);
    }


    @Override //explain
    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }
    public static ArrayList<TempGame> gettempGameArrayList() {
        return tempGameArrayList;
    }

    public static String removeTempGame(String gameName){
        Iterator<TempGame> iterator= tempGameArrayList.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().getGameName().equalsIgnoreCase(gameName)) {
                iterator.remove();
            }
        }
        return HandlerModel.gameListMessage();
    }

}

