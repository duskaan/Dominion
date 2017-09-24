package Handlers;

import Server.ClientThread;
import com.sun.deploy.util.SessionState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.GAME.toString();


    public GameMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, 0);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameMessageHandler() {

    }

    @Override
    public void handleMsg(String message) throws UnknownFormatException {
        //get the game name --> String gameName = splitMessage(message,3);
        //Game game = GameManager.getGame(gameName);
        //returnMessage = game.handleMessage()

        //die unteren muessen zu damiano in das jeweilige Game
        String subHandler = splitMessage(message, SUBHANDLER);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        handler.handleMsg(message);

    }

    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        getWriteOtherClients().writeToGameClients(newMessage); //todo should i have it here or at the new writer for new cards or played cards (in case the turn isnt allowed)
        super.write(newMessage);
    }


}
