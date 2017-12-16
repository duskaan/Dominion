package Handlers;

/**
 * Created by Tim on 05.12.2017.
 */
import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;
import Server.LogHandling;
import Server.Player;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Tim on 14.09.2017.
 */
public class GameJoinGameMessageHandler extends GameMessageHandler {

    private final String CLASSNAME = GameMessageType.JOINGAME.toString();
    private  MessageHandler superHandler;
    private String message =null;

    public GameJoinGameMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }
    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }
    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message=msgIn;
        String gameName = splitMessage(message, 2);//todo set Token

        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        for(int i = 0; ServerMessageHandler.gettempGameArrayList().size()<i; i++) {
            if (ServerMessageHandler.gettempGameArrayList().get(i).getPlayerList().contains(player)){
                ServerMessageHandler.gettempGameArrayList().get(i).removePlayer(player);
                LogHandling.logOnFile(Level.INFO, player.toString()+" is removed from the TempGame");
            } //first checked if the player is in the game and removed
            if(ServerMessageHandler.gettempGameArrayList().get(i).getGameName().equalsIgnoreCase(gameName)){
                ServerMessageHandler.gettempGameArrayList().get(i).addPlayer(player);
                player.setGameName(gameName);
                LogHandling.logOnFile(Level.INFO, player.toString()+" is added to the Game "+gameName);
                if(ServerMessageHandler.gettempGameArrayList().get(i).getPlayerList().size()==ServerMessageHandler.gettempGameArrayList().get(i).getMaxPlayer()){
                    GameStartGameMessageHandler gameStartHandler = new GameStartGameMessageHandler();
                    gameStartHandler.handleMessage(message, superHandler);
                }
            }//after it is checked if the player is in the game and the name of the game are the same the player is added
        }
        write(HandlerModel.gameListMessage(),false);
    }

    public String getMessage(){
        return message;
    }

    public Socket getClientSocket(){
        return superHandler.getClientSocket();
    }

}
