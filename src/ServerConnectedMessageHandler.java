/**
 * Created by Tim on 12.09.2017.
 */
public class ServerConnectedMessageHandler extends MessageHandler
{    private final String CLASSNAME = ServerMessageType.CONNECTED.toString();

    public ServerConnectedMessageHandler(String message) throws UnknownFormatException {
      if(!CLASSNAME.equals(message)){
        throw new UnknownFormatException(message);
         }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage= "This player is connected";
        //bring player into loginField
        return returnMessage;
    }
}
