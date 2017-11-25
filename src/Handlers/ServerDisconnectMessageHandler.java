package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerDisconnectMessageHandler extends ServerMessageHandler{
   private final String CLASSNAME = ServerMessageType.DISCONNECT.toString();
    private String message =null;
    private MessageHandler superHandler;

    public ServerDisconnectMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        //return player to loginField +end game+ end thread

    }
    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        superHandler.write(newMessage);
    }
    public String getMessage(){
        return message;
    }
}