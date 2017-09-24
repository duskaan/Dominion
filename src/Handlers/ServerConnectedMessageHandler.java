package Handlers;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerConnectedMessageHandler extends ServerMessageHandler {
    private final String CLASSNAME = ServerMessageType.CONNECTED.toString();
    private String message = null;

    public ServerConnectedMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message = msgIn;
        //bring player into loginField

    }

    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }

    public String getMessage() {
        return message;
    }
}
