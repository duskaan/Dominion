/**
 * Created by Tim on 13.09.2017.
 */
public class ServerOkMessageHandler extends MessageHandler{
    private final String CLASSNAME = ServerMessageType.OK.toString();

    public ServerOkMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage = null;

        //send message login successful
        //show lobby with option of opening new game or entering a game + chat

        return returnMessage;
    }
}
