package Handlers;

import Server.LogHandling;
import Server.Player;

import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerLoginMessageHandler extends ServerMessageHandler  {
    private final String CLASSNAME = ServerMessageType.LOGIN.toString();
    private String message = null;
    private MessageHandler superHandler;

    public ServerLoginMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerLoginMessageHandler(){
    }

    public void write(String message,Boolean privateMessage) {
        message = addDelimiter(message);
        String newMessage = CLASSNAME + message;
        superHandler.write(newMessage,privateMessage);
    }

    @Override
    public  void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        String playerName = splitMessage(message, 2);//todo set token
        Player player = socketPlayerHashMap.get(getClientSocket().getPort());


        String password = splitMessage(message, 3); //todo set token
        boolean successful = HandlerModel.tryToLogin(playerName, password);
        String returnMessage= null;
        if(successful){
            player.setPlayerName(playerName);
            lobbyList.add(player);

            returnMessage = "successful@"+playerName; //send gamelist and topfive and then a successfull message
            write(returnMessage, true);
            ServerLobbyMessageHandler lobbyHandler = new ServerLobbyMessageHandler();
            lobbyHandler.handleMessage("SERVER@LOBBY@TOPFIVE",superHandler);
            lobbyHandler.handleMessage("SERVER@LOBBY@GAMELIST", superHandler);

        }else{
            write("loginFailed", true);
        }
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String splitMessage(String message, int tokenIndex) {
        return super.splitMessage(message, tokenIndex);
    }

    public Socket getClientSocket(){
        return superHandler.getClientSocket();
    }

}
