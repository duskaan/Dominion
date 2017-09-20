package Models;

import DataBasePackage.Database;
import Handlers.MessageHandler;
import Handlers.MessageHandlerFactory;
import Handlers.ServerLoginMessageHandler;
import javafx.beans.value.ObservableStringValue;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Tim on 20.09.2017.
 */
public class LoginModel implements Observer {
    private String observableMessage = null;
    public static final int USERNAMEINDEX = 4;
    public static final int PASSWORDINDEX = 5;

    public LoginModel() {
        //this.observableMessage = observableMessage;

    }

    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof ServerLoginMessageHandler) {
            ServerLoginMessageHandler newHandler = (ServerLoginMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
            login(sMessage);
        }

    }

    public void login(String[] sMessage) {
        if (loginCheck(sMessage)) {//if successful
            //String message = handler.alterMessage(msgIn,"okMessageHandler",3);
            //MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
            //handler.handleMsg(message);
            //send message to okMessageHandler with first altering the original msgIn and then handler.HandleMessage...
        } else { //if login failed
            //String message = alterMessage(msgIn,"failedMessageHandler",3);
            //MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
            //handler.handleMsg(message);
            //send message to failedMessageHandler


        }


    }


    public boolean loginCheck(String[] sMessage) {
        boolean loginSuccessful = false;

        String userName = sMessage[USERNAMEINDEX];
        String password = sMessage[PASSWORDINDEX];
        loginSuccessful = Database.getDatabase().login(userName, password);

        //todo create player+ socket map
        return loginSuccessful;
    }

}
