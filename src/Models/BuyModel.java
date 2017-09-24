package Models;

import Handlers.GameBuyMessageHandler;
import Handlers.ServerNewGameMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class BuyModel implements Observer {
    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof GameBuyMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            GameBuyMessageHandler newHandler = (GameBuyMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
//todo programm handling

        }
    }
}

