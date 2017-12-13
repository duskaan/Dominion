package Handlers;

import Server.LogHandling;

import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Tim on 27.11.2017.
 */
public class ServerLobbyMessageHandler extends ServerMessageHandler {
    private final String CLASSNAME = ServerMessageType.LOBBY.toString();
    private String message = null;
    private MessageHandler superHandler;
    String replyMessage;


    public ServerLobbyMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public ServerLobbyMessageHandler() {
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
        String fiveOrGamesList = splitMessage(message, 2);
        LogHandling.logOnFile(Level.INFO,"MessageType: " +fiveOrGamesList);

        if (fiveOrGamesList.equalsIgnoreCase("TOPFIVE")) {
            replyMessage = HandlerModel.topFiveMessage();
            LogHandling.logOnFile(Level.INFO,"ReplyMessage is: "+ replyMessage);
            write(replyMessage,false);
        } else if (fiveOrGamesList.equalsIgnoreCase("GAMESLIST")) {
            replyMessage = HandlerModel.gameListMessage();
            write(replyMessage,false);
        }
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String splitMessage(String message, int tokenIndex) {
        return super.splitMessage(message, tokenIndex);
    }

    public Socket getClientSocket() {
        return superHandler.getClientSocket();
    }


}

