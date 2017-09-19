
/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String [ ] args){
        initServer();
        new DominionServer().start();
    }

    private static void initServer() {
        //initiates the server with the messageHandlers, the DataBase connection and the logger
        addMessageHandler();
        //todo: database connection + logger
    }

    private static void addMessageHandler() {

        //add all messsagehandler
        MessageHandlerFactory.addHandler("GameMessageHandler");
        MessageHandlerFactory.addHandler("GameActionMessageHandler");

        MessageHandlerFactory.addHandler("ServerMessageHandler");
        MessageHandlerFactory.addHandler("ServerConnectedMessageHandler");
        MessageHandlerFactory.addHandler("ServerDisconnectMessageHandler");
        MessageHandlerFactory.addHandler("ServerLoginMessageHandler");
        MessageHandlerFactory.addHandler("ServerOkMessageHandler");
        MessageHandlerFactory.addHandler("ServerFailedMessageHandler");
        MessageHandlerFactory.addHandler("ServerRegisterMessageHandler");

        //TODO:und so weiter
    }
}
