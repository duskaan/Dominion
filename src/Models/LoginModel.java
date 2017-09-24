package Models;

import Database.Database;
import Handlers.ServerLoginMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 20.09.2017.
 */
public class LoginModel implements Observer {
    public static final int USERNAMEINDEX = 4;
    public static final int PASSWORDINDEX = 5;
    String userName;

    public LoginModel() {
        //this.observableMessage = observableMessage;

    }

    @Override
    public void update(Observable o, Object handler) {
        if (handler instanceof ServerLoginMessageHandler) {
            LogHandling.logOnFile(Level.INFO,"Login into Database is started");
            ServerLoginMessageHandler newHandler = (ServerLoginMessageHandler) handler;
            String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
            boolean successful = tryToLogin(sMessage);
            informAboutLogin(successful, newHandler);

        }

    }

    private void informAboutLogin(boolean successful, ServerLoginMessageHandler newHandler) {
        if(successful){
            LogHandling.logOnFile(Level.INFO,"Login of "+userName +"was successful");
            WriteOtherClients.getMessageHandler().setUserName(userName); //todo does this work?
            newHandler.write("");//login was successful
        }else{
            LogHandling.logOnFile(Level.INFO,"Login of "+userName +"was NOT successful");
            newHandler.write("");//login was not successful
        }

    }


    public boolean tryToLogin(String[] sMessage) {
        boolean loginSuccessful;

        userName = sMessage[USERNAMEINDEX];
        String password = sMessage[PASSWORDINDEX];
        loginSuccessful = Database.getDatabase().login(userName, password);

        //todo create player+ socket map
        return loginSuccessful;
    }

}
