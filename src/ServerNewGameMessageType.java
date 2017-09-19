import java.net.Socket;

/**
 * Created by Tim on 14.09.2017.
 */
public class ServerNewGameMessageType extends MessageHandler{
    private final String CLASSNAME = ServerMessageType.NEWGAME.toString();

    public ServerNewGameMessageType(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage = null;

        //initGame();
        //send message login successful
        //show lobby with option of opening new game or entering a game + chat

        return returnMessage;
    }
    private void initGame(Socket ClientSocket, String gameName){

}

}
