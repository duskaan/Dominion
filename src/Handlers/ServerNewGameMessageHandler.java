package Handlers;

import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;
import Server.Player;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Tim on 14.09.2017.
 */
public class ServerNewGameMessageHandler extends ServerMessageHandler {

    private final String CLASSNAME = ServerMessageType.NEWGAME.toString();
    private MessageHandler superHandler;
    private String message = null;

    public ServerNewGameMessageHandler(String message) throws UnknownFormatException {
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
    //creates a new TempGame with the gameName, cardNumbers and Maxplayers
    //sends an updated gamelist to all clients in the lobby
    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        String gameName = splitMessage(message, 2);
        int cardNumbers = Integer.parseInt(splitMessage(message, 3));
        int maxPlayers = Integer.parseInt(splitMessage(message, 4));

        Player player = socketPlayerHashMap.get(getClientSocket().getPort());

        gettempGameArrayList().add(new TempGame(gameName, cardNumbers, player, maxPlayers));
        ServerLobbyMessageHandler lobbyHandler = new ServerLobbyMessageHandler();
        lobbyHandler.handleMessage("SERVER@LOBBY@GAMELIST", superHandler);


    }

    public String getMessage() {
        return message;
    }

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }

}
