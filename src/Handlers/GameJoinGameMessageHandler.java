package Handlers;


import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;
import Server.LogHandling;
import Server.Player;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;


/**
 * Created by Tim on 05.12.2017.
 */
public class GameJoinGameMessageHandler extends GameMessageHandler {

    private final String CLASSNAME = GameMessageType.JOINGAME.toString();
    private MessageHandler superHandler;
    private String message = null;

    public GameJoinGameMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public void write(String message, Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage, privateMessage);
    }
    //@Tim
    //the player is added to the TempGame
    //if the TempGame is full the game is started and removed from the tempGame lists
    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        String gameName = splitMessage(message, 2);

        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        Iterator<TempGame> iterator = ServerMessageHandler.gettempGameArrayList().iterator();
        TempGame tempGame;

        while (iterator.hasNext()) {
            tempGame = iterator.next();
            LogHandling.logOnFile(Level.INFO, tempGame.getGameName());
            if (tempGame.getPlayerList().contains(player)) {

                tempGame.removePlayer(player);
                LogHandling.logOnFile(Level.INFO, player.toString() + " is removed from " + tempGame.getGameName());
            } else {
                //LogHandling.logOnFile(Level.INFO, player + " is not removed from any game");
            }
            if (tempGame.getGameName().equalsIgnoreCase(gameName)) {
                tempGame.addPlayer(player);
                player.setGameName(gameName);
                LogHandling.logOnFile(Level.INFO, player.toString() + " is added to the Game " + gameName);
                if (tempGame.getPlayerList().size() == tempGame.getMaxPlayer()) {
                    writeToPrivate(tempGame.getPlayerList());
                    LogHandling.logOnFile(Level.INFO, gameName + " is full and will be started");
                    GameStartGameMessageHandler gameStartHandler = new GameStartGameMessageHandler();
                    gameStartHandler.setSuperHandler((GameMessageHandler) superHandler);
                    gameStartHandler.startGame(tempGame);
                    iterator.remove();
                }
            }
        }
    }

    private void writeToPrivate(ArrayList<Player> playerList) {



    }


    public String getMessage() {
        return message;
    }

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }

}
