package Models;

import Handlers.GameMessageHandler;
import Handlers.GameStartGameMessageHandler;
import Handlers.ServerLoginMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class StartGameModel implements Observer {
    public StartGameModel(){

    }
    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof GameStartGameMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            GameStartGameMessageHandler newHandler = (GameStartGameMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
//todo programm handling

        }
    }
}
