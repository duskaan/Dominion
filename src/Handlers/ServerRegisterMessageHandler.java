package Handlers;

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

		String returnMessage = null;

	}

	public void write(String message) {
		String tempMessage = addDelimiter(message);
		String newMessage = CLASSNAME + tempMessage;
		superHandler.write(newMessage);
	}

	public String getMessage() {
		return message;
	}
}
