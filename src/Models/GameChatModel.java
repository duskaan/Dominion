package Models;

import Handlers.ServerGameChatMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class GameChatModel implements Observer {
    @Override
    public void update(Observable o, Object handler) {
        LogHandling.logOnFile(Level.INFO, "New Game is initiated");
        if (handler instanceof ServerGameChatMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");

            ServerGameChatMessageHandler newHandler = (ServerGameChatMessageHandler) handler;

            chatting(newHandler);
//todo programm handling
        }
    }

    private void chatting(ServerGameChatMessageHandler newHandler) {
        String message = newHandler.getMessage();
        String[] sMessage = newHandler.splitMessage(message);
        String userName = sMessage[3];
        //String[] playingClients = getGameUserName(userName);


        //newHandler.getWriteOtherClients().writeToGameClients(message, playingClients);
    }
    /*private String[] getGameUserName(String userName){
        String[] users = Game.getUSers(userName);
        return users;
    }*/
}

