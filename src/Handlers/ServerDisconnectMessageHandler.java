package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerDisconnectMessageHandler extends MessageHandler{
   private final String CLASSNAME = ServerMessageType.DISCONNECT.toString();
    private String message =null;

    public ServerDisconnectMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {

        //return player to loginField +end game+ end thread

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