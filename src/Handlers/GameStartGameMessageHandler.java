package Handlers;

import GameLogic.Game;
import Server.LogHandling;
import Server.Player;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Tim on 23.09.2017.
 */
public class GameStartGameMessageHandler extends GameMessageHandler {
    private final String CLASSNAME = GameMessageType.STARTGAME.toString();
    private String message = null;
    private GameMessageHandler superHandler;
    ArrayList<TempGame> list;

    public GameStartGameMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameStartGameMessageHandler() {

    }

    public void write(String message, Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage, privateMessage);
    }


    public void handleMessage(String msgIn, GameMessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;

    }


    public String getMessage() {
        return message;
    }


    public void startGame(TempGame tempGame) {
        String gameName = tempGame.getGameName();
        String[] playerArray = new String[tempGame.getPlayerList().size()];
        int cardsInGame = tempGame.getCardsInGame();
        String confiqMessage= "*config@"+cardsInGame+"@";
        int i=0;
        for (Player player:tempGame.getPlayerList()) {
            playerArray[i] = player.getPlayerName();
            System.out.println(player.getPlayerName());
            confiqMessage+= playerArray[i]+"/";
            i++;

        }

        LogHandling.logOnFile(Level.INFO, playerArray.toString());

        Game game = new Game(gameName, cardsInGame, playerArray);
        MessageHandler.removeFromLobbyList(playerArray);
        MessageHandler.addToGameMap(tempGame.getPlayerList(), game);
        write(confiqMessage, false);
        superHandler.listenForMessage(game);
        game.startGame();
        String playerNames = "PlayerList@";
        for (String player : playerArray) {
            System.out.println(player);
            playerNames += player + "/";
        }
        //write(playerNames, false);



        LogHandling.logOnFile(Level.INFO, tempGame + "is started");
    }

    public void setSuperHandler(GameMessageHandler superHandler) {
        this.superHandler=superHandler;
    }
}
