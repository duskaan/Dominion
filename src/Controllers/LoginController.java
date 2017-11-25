package Controllers;

import Database.Database;
import Handlers.ServerLoginMessageHandler;
import Server.LogHandling;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

/**
 * Created by Tim on 20.09.2017.
 */
public class LoginController implements Observer {
	private static final int USERNAMEINDEX = 4;
	private static final int PASSWORDINDEX = 5;
	private String userName;

	public LoginController() {
	}

	@Override
	public void update(Observable o, Object handler) {
		if (handler instanceof ServerLoginMessageHandler) {
			ServerLoginMessageHandler newHandler = (ServerLoginMessageHandler) handler;
			//String[] sMessage = newHandler.splitMessage(newHandler.getMessage());
			//boolean successful = tryToLogin(sMessage);
			//informAboutLogin(successful, newHandler);
		}

	}

	private boolean tryToLogin(String[] sMessage) {
		userName = sMessage[USERNAMEINDEX];
		String password = sMessage[PASSWORDINDEX];
		return Database.getDatabase().login(userName, password);
	}

	private void informAboutLogin(boolean successful, ServerLoginMessageHandler newHandler) {
		if (successful) {
			LogHandling.logOnFile(Level.INFO, "Login of " + userName + "was successful");
			newHandler.write("");//login was successful
		} else {
			LogHandling.logOnFile(Level.INFO, "Login of " + userName + "was NOT successful");
			newHandler.write("");//login was not successful
		}
	}

}

/**
 * DO not USE THESE MODELS. JUST PUT IT IN THE SUBHANDLERS. THAT IS FAR ENOUGH ENCAPSULATION!!!!!!!!!!!!!!!
 */
