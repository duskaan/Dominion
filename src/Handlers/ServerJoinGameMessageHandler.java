package Handlers;

/**
 * Created by Tim on 05.12.2017.
 */
import Handlers.MessageHandler;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;
import Server.Player;
import java.net.Socket;

/**
 * Created by Tim on 14.09.2017.
 */
public class ServerJoinGameMessageHandler extends ServerMessageHandler {

    private final String CLASSNAME = ServerMessageType.NEWGAME.toString();
    private  MessageHandler superHandler;
    private String message =null;

    public ServerJoinGameMessageHandler(String message) throws UnknownFormatException {
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
        //GameMessageHandler.games.add(new TempGame(...))
        String gameName = splitMessage(message, 5);//todo set Token

        Player player = socketPlayerHashMap.get(getClientSocket().getInetAddress());
        for(int i = 0; ServerMessageHandler.gettempGameArrayList().size()<i; i++){
            if(ServerMessageHandler.gettempGameArrayList().get(i).getGameName().equalsIgnoreCase(gameName)){
                ServerMessageHandler.gettempGameArrayList().get(i).addPlayer(player);
                player.setGameName(gameName);
            }
        }


        //initGame();
        write(HandlerModel.gameListMessage(),false);


    }

    public String getMessage(){
        return message;
    }

    public Socket getClientSocket(){
        return superHandler.getClientSocket();
    }

}
