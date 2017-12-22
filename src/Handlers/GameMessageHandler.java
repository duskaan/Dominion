package Handlers;

import Database.Database;
import GameLogic.Game;
import Server.Player;

import java.io.Serializable;
import java.net.Socket;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler {
    private MessageHandler superHandler;

    private final String CLASSNAME = MessageType.GAME.toString();


    public GameMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAIN_HANDLER_INDEX);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameMessageHandler() {

    }

    //@Tim
    //it sends the message to the subHandler if it is a JoinGame,
    //else it sends the message to the corresponding game
    @Override
    public void handleMessage(String message, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;

        String subHandler = splitMessage(message, SUB_HANDLER_INDEX);

        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        if (subHandler.equalsIgnoreCase("JOINGAME")) {
            MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
            handler.handleMessage(message, this);
        } else {
            gameList.get(player).readMessage(message);
        }


    }

    public void write(String message, Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage, privateMessage);
    }


    private void endGame(String newValue) {
        //endGame@player1,33@player2,11
        String[] messageParts = split(newValue, "@");
        String[] subArray = Arrays.copyOfRange(messageParts, 1, messageParts.length);
        //[player1,33] [player2,11]
        String[] playerNames = new String[subArray.length];
        Integer[] playerScores = new Integer[subArray.length];
        int highScore = 0;
        for (int i = 0; i < subArray.length; i++) {
            playerNames[i] = split(subArray[i], ",")[0];
            playerScores[i] = Integer.parseInt(split(subArray[i], ",")[1]);
            if (playerScores[i] > highScore) {
                highScore = playerScores[i];
            }
        }
        for (int i = 1; i < playerNames.length; i++) {
            Database.getDatabase().updateAfterGame(playerNames[i], playerScores[i], highScore);
        }
        addAndRemoveFromLists(); //todo set time when to do this
    }

    //@Tim
    //removes it from the gamelist and adds it to the lobbylist
    private void addAndRemoveFromLists() {
        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        ArrayList<Player> list = getKeyByValue(gameList, gameList.get(player));
        addLobbyList(list);
        deleteGameFromList(list);
    }

    private void topFiveUpdate() {

    }

    //@Tim
    //removes players from finished Game from the gameList
    private void deleteGameFromList(ArrayList<Player> listToRemove) {

        for (int i = 0; listToRemove.size() > i; i++) {
            gameList.remove(listToRemove.get(i));
        }
    }

    //@Tim
    //adds players from finished game
    private void addLobbyList(ArrayList<Player> listToAdd) {
        for (int i = 0; listToAdd.size() > i; i++) {
            lobbyList.add(listToAdd.get(i));
        }
    }


    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }

    //@Tim
    //listens if the gamelogic changes the message it is checked if it is an end message
    //if it is not it is send to the clients of that game
    //the / is added to make it easier to split the message at the client side
    void listenForMessage(Game game) {
        game.getGameResponseMessage().addListener((observable, oldValue, newValue) -> {
            if (splitMessage(newValue, 0).equalsIgnoreCase("end")) {
                endGame(newValue);
            }
            write("/" + newValue, false);
        });
    }

    private String[] split(String message, String splitter) {
        return message.split(splitter);
    }
}
