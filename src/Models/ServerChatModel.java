package Models;

import Handlers.ServerChatMessageHandler;
import Handlers.ServerNewGameMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class ServerChatModel implements Observer {
    @Override
    public void update(Observable o, Object handler) {
        LogHandling.logOnFile(Level.INFO, "New Game is initiated");
        if (handler instanceof ServerChatMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            ServerChatMessageHandler newHandler = (ServerChatMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());

//todo programm handling
        }
    }
}
