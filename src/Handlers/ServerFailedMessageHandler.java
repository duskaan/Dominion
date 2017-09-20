package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

/**
 * Created by Tim on 13.09.2017.
 */
public class ServerFailedMessageHandler extends MessageHandler {
    private final String CLASSNAME = ServerMessageType.FAILED.toString();
    private String message =null;

    public ServerFailedMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }


    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message=msgIn;
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
