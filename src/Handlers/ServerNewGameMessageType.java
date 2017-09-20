package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

import java.net.Socket;

/**
 * Created by Tim on 14.09.2017.
 */
public class ServerNewGameMessageType extends MessageHandler {

    private final String CLASSNAME = ServerMessageType.NEWGAME.toString();
    private String message =null;

    public ServerNewGameMessageType(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message=msgIn;

        splitMessage(msgIn);
        //initGame();
        //send message login successful
        //show lobby with option of opening new game or entering a game + chat


    }
    private void initGame(Socket ClientSocket, String gameName){

    }
    public String getMessage(){
        return message;
    }

}
