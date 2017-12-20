package Handlers;

import Database.Database;
import GameLogic.Game;
import Server.Player;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 * Created by Tim on 23.08.2017.
 */
public class GameMessageHandler extends MessageHandler implements Observer {
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

    @Override
    public void handleMessage(String message, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;

        String subHandler = splitMessage(message, SUB_HANDLER_INDEX);

        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        if (gameList.get(player) == null || subHandler.equalsIgnoreCase("ENDGAME")||subHandler.equalsIgnoreCase("JOINGAME")) {
            MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
            handler.handleMessage(message, this);
        } else {

            gameList.get(player).readMessage(message);
        }


    }

    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }


    @Override
    public void update(Observable o, Object arg) {
        String response = (String) arg;
        if (splitMessage(response, 0).equalsIgnoreCase("end")) {
            endGame();
        }
        write(response, false);
        //todo what to do with the response?
    }

    private void endGame() {

        topFiveUpdate();

        //showLobby();
        addAndRemoveFromLists();

    }

    private void addAndRemoveFromLists() {
        Player player = socketPlayerHashMap.get(getClientSocket().getPort());
        ArrayList<Player> list = getKeyByValue(gameList, gameList.get(player));
        addLobbyList(list);
        deleteGameFromList(list);
    }

    private void topFiveUpdate() {

        //Database.getDatabase().updateAfterGame();
    }

    private void deleteGameFromList(ArrayList<Player> listToRemove) {

        for (int i = 0; listToRemove.size() > i; i++) {
            gameList.remove(listToRemove.get(i));
        }
    }
    private void addLobbyList(ArrayList<Player> listToAdd) {
        for (int i = 0; listToAdd.size() > i; i++) {
            lobbyList.add(listToAdd.get(i));
        }
    }
    //sobald nachricht hierhin kommt muss geprüft werden ob game = null ist. wenn ja dann wird diese method ausgelöst?

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }

    void listenForMessage(Game game) {
        game.getGameResponseMessage().addListener((observable, oldValue, newValue) -> {
            if (splitMessage(newValue, 0).equalsIgnoreCase("end")) {
                endGame();
            }
            write("/"+newValue, false);
        });
    }
}
