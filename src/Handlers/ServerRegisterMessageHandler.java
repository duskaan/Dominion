package Handlers;

import Database.Database;

/**
 * Created by Tim on 13.09.2017.
 */
public class ServerRegisterMessageHandler extends ServerMessageHandler {
    private String message =null;
    private final String CLASSNAME = ServerMessageType.REGISTER.toString();

    public ServerRegisterMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerRegisterMessageHandler(){

    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message = msgIn;
        String returnMessage = null;

    }

    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }
    public String getMessage(){
        return message;
    }
}
