package Handlers;

import GameLogic.Game;
import Server.Player;

import java.util.ArrayList;

/**
 * Created by Tim on 23.09.2017.
 */
public class GameStartGameMessageHandler extends GameMessageHandler {
    private final String CLASSNAME = GameMessageType.STARTGAME.toString();
    private String message = null;
    private MessageHandler superHandler;
    ArrayList<TempGame> list;

    //List<Observer> observers;


    public GameStartGameMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameStartGameMessageHandler() {

    }

    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        String gameName = splitMessage(message, 5); //todo set token

        String[] playerArray = null;
        ArrayList<Player> players;
        int cardsInGame=0;
        list = ServerMessageHandler.gettempGameArrayList();
        for (int i = 0; list.size() < i; i++) {
            if (list.get(i).getGameName().equals(gameName)) {
                players = list.get(i).getPlayerList();
                for (int j = 0; players.size() < j; j++) {
                    players.get(j).setGameName(gameName);
                    playerArray[j]=players.get(j).getPlayerName();
                }
                cardsInGame=list.get(i).getCardsInGame();
            }
        }
        if(playerArray!=null ||cardsInGame!=0){
            Game game= new Game(gameName, cardsInGame, playerArray);
            game.addObserver(superHandler);
            //ServerMessageHandler.removeTempGame(gameName);
        }


        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model
    }

    public String getMessage() {
        return message;
    }


}
