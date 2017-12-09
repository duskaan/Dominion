package Handlers;

/**
 * Created by Tim on 23.09.2017.
 */
public class ServerChatMessageHandler extends GameMessageHandler {
    private final String CLASSNAME = ServerMessageType.CHAT.toString();
    private String message = null;
    private MessageHandler superHandler;


    public ServerChatMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerChatMessageHandler() {
    }
    public void write(String message, Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage, privateMessage);
    }
    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;

        String chatMessage = splitMessage(message, 5); //todo set token
        String playerName = splitMessage(message, 4); //todo set token
        sendChat(chatMessage, playerName);
    }
    private void sendChat(String chatMessage, String playerName) {
        write(playerName + ": " + chatMessage, false);
    }

    public String getMessage() {
        return message;
    }
}


