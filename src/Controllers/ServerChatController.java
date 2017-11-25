package Controllers;

import Handlers.ServerLobbyChatMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class ServerChatController implements Observer {
    @Override
    public void update(Observable o, Object handler) {
        LogHandling.logOnFile(Level.INFO, "New GameHandlers is initiated");
        if (handler instanceof ServerLobbyChatMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            ServerLobbyChatMessageHandler newHandler = (ServerLobbyChatMessageHandler) handler;
            chatting(newHandler);

//todo programm handling
        }
    }
    private void chatting(ServerLobbyChatMessageHandler newHandler) {
        String sMessage = newHandler.getMessage();
        //newHandler.getWriteOtherClients().writeToLobbyClients(sMessage);
    }
}
