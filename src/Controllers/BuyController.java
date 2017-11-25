package Controllers;

import Handlers.GameBuyMessageHandler;
import Models.BuyModel;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class BuyController implements Observer {
    BuyModel buyModel;
    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof GameBuyMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "Login into Database is started");
            GameBuyMessageHandler newHandler = (GameBuyMessageHandler) handler;
            //String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
//todo programm handling

        }
    }
}

