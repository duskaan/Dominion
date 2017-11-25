package Controllers;

import Handlers.ServerNewGameMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 20.09.2017.
 */
public class NewGameController implements Observer{
    @Override
    public void update(Observable o, Object handler) {
        LogHandling.logOnFile(Level.INFO, "New GameHandlers is initiated");
        if (handler instanceof ServerNewGameMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            ServerNewGameMessageHandler newHandler = (ServerNewGameMessageHandler) handler;
            //String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
//todo programm handling

        }
    }
}
