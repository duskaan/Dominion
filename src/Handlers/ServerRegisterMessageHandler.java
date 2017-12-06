package Handlers;

import Server.LogHandling;
import Server.Player;

import java.util.logging.Level;

/**
 * Created by Tim on 13.09.2017.
 */
public class ServerRegisterMessageHandler extends ServerMessageHandler {
	private String message = null;
	private final String CLASSNAME = ServerMessageType.REGISTER.toString();
	private MessageHandler superHandler;

	public ServerRegisterMessageHandler(String message) throws UnknownFormatException {
		if (!CLASSNAME.equals(message)) {
			throw new UnknownFormatException(message);
		}
	}

	public ServerRegisterMessageHandler() {

	}

	@Override
	public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
		this.superHandler = superHandler;
		message = msgIn;
		String userName = splitMessage(message, 5);//todo set token
		String password = splitMessage(message, 6);//todo set token
		Boolean successful = HandlerModel.register(userName, password);
		LogHandling.logOnFile(Level.INFO,"Successful: "+successful.toString());
		Player player = socketPlayerHashMap.get(getClientSocket().getInetAddress());
		if(successful){
			player.setPlayerName(message);
			lobbyList.add(player);
			write(HandlerModel.gameListMessage(), true);
			write(HandlerModel.topFiveMessage(), true);
			write("Registration successful",true);
		}else{
			write("Registration failed",true);
		}
	}

	public void write(String message,Boolean privateMessage) {
		message = addDelimiter(message);
		String newMessage = CLASSNAME + message;
		superHandler.write(newMessage,privateMessage);
	}

	public String getMessage() {
		return message;
	}
}
