/**
 * Created by Tim on 13.09.2017.
 */
public class ServerFailedMessageHandler extends MessageHandler {
    private final String CLASSNAME = ServerMessageType.FAILED.toString();

    public ServerFailedMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }


    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage = null;


        return returnMessage;
    }
}
