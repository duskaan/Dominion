import Handlers.MessageHandlerFactory;

/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String [ ] args){
        initServer();
        new DominionServer().start();
    }

    private static void initServer() {
        //initiates the server with the messageHandlers, the DataBasePackage.DataBase connection and the logger
        addMessageHandler();
        //todo: database connection + logger
    }

    private static void addMessageHandler() {

        //add all messsagehandler
        MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
        MessageHandlerFactory.addHandler("GameActionMessageHandler");

        MessageHandlerFactory.addHandler("Handlers.ServerMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerConnectedMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerDisconnectMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerLoginMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerOkMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerFailedMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerRegisterMessageHandler");

        //TODO:und so weiter
    }
}
