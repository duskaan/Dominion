package Handlers;

/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.GAME.toString();

    public GameMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, 0);
        if(!CLASSNAME.equals(mainHandler)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public void handleMsg(String message) throws UnknownFormatException {
        String returnMessage = null;

        //get the game name --> String gameName = splitMessage(message,3);
        //Game game = GameManager.getGame(gameName);
        //returnMessage = game.handleMessage()

        //die unteren muessen zu damiano in das jeweilige Game
        String subHandler = splitMessage(message, SUBHANDLER);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        handler.handleMsg(message);

    }



}
