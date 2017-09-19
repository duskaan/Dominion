package Handlers;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerDisconnectMessageHandler extends MessageHandler
{    private final String CLASSNAME = ServerMessageType.DISCONNECT.toString();

    public ServerDisconnectMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage= "This player is disconnected";
        //return player to loginField +end game+ end thread
        return returnMessage;
    }
}