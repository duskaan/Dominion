package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;

import java.net.Socket;

/**
 * Created by Tim on 14.09.2017.
 */
public class ServerNewGameMessageHandler extends ServerMessageHandler {

    private final String CLASSNAME = ServerMessageType.NEWGAME.toString();
    private  MessageHandler superHandler;
    private String message =null;

    public ServerNewGameMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message=msgIn;
        //GameMessageHandler.games.add(new Game(...))

        //splitMessage(msgIn); todo fix it
        //initGame();
        //send message login successful
        //show lobby with option of opening new game or entering a game + chat


    }

    public String getMessage(){
        return message;
    }

}
