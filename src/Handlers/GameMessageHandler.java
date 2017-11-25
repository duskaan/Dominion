package Handlers;

import Server.Player;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler {
    private MessageHandler superHandler;

    private final String CLASSNAME = MessageType.GAME.toString();
    private Game game = null; //todo arraylist mit den Games
    public static ArrayList<Game> games= new ArrayList<>();


    public GameMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAIN_HANDLER_INDEX);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameMessageHandler() {

    }

    @Override
    public void handleMessage(String message, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        //get the game name --> String gameName = splitMessage(message,3);
        //GameHandlers game = GameManager.getGame(gameName);
        //returnMessage = game.handleMessage()

        //die unteren muessen zu damiano in das jeweilige GameHandlers
        String subHandler = splitMessage(message, SUB_HANDLER_INDEX);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        handler.handleMessage(message,this);


    }

    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        //getWriteOtherClients().writeToGameClients(); //todo should i have it here or at the new writer for new cards or played cards (in case the turn isnt allowed)
        superHandler.write(newMessage);
    }
    public void createGame(String gameName, boolean computer, String userNames){
        game = new Game(gameName, computer, userNames); //game hat player. wenn nachricht von
    }
    //sobald nachricht hierhin kommt muss geprüft werden ob game = null ist. wenn ja dann wird diese method ausgelöst?


}
