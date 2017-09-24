package Models;

import Database.Database;
import Handlers.ServerLoginMessageHandler;
import Handlers.ServerRegisterMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 20.09.2017.
 */
public class RegisterModel implements Observer {
    final int USERNAME = 4;
    final int PASSWORD = 5;

    public RegisterModel() {

    }

    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof ServerRegisterMessageHandler) {
            LogHandling.logOnFile(Level.INFO, "new Player is registering");
            ServerRegisterMessageHandler newHandler = (ServerRegisterMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
            newHandler.write(register(sMessage));
        }
    }

    private String register(String[] sMessage) {
        String userName = sMessage[USERNAME];
        String password = sMessage[PASSWORD];
        String returnMessage = null;
        if (register(userName, password)) {//if register successful
            returnMessage = "register successful";
            //send message to okMessageHandler with first altering the original msgIn and then handler.HandleMessage...
        } else { //if register failed
            returnMessage = "Username already in use";
        }
        return returnMessage;

    }

    public boolean register(String userName, String password) {
        boolean registerSuccessful = false;

        registerSuccessful = Database.getDatabase().insert(userName, password); //only registers if the name does not exist

        return registerSuccessful;
    }
}

