package Handlers;

import Server.ClientThread;


/**
 * Created by Tim on 13.09.2017.
 */
public class ServerOkMessageHandler extends MessageHandler {
    private String message =null;
    private final String CLASSNAME = ServerMessageType.OK.toString();

    public ServerOkMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message=msgIn;

        //send message login successful
        //show lobby with option of opening new game or entering a game + chat
        //put the username and the socket into a map while damiano has the game and username
        //ClientThread.addPlayer();

    }
    public String getMessage(){
        return message;
    }
}
