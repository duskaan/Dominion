package Handlers;


/**
 * Created by Tim on 13.09.2017.
 */
public class ServerOkMessageHandler extends ServerMessageHandler {
    private String message =null;
    private final String CLASSNAME = ServerMessageType.OK.toString();
    private MessageHandler superHandler;

    public ServerOkMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message=msgIn;

        //send message login successful
        //show lobby with option of opening new game or entering a game + chat
        //put the username and the socket into a map while damiano has the game and username
        //Player.addPlayer();

    }
    public String getMessage(){
        return message;
    }
}
