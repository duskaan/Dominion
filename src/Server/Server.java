package Server;

import DataBasePackage.Database;
import Handlers.MessageHandlerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String [ ] args){

        initServer();
        new DominionServer().start();
    }

    private static void initServer() {
        Logger logger= Logger.getLogger("Server");
        logger.log(Level.INFO,"Server.Server application started");
        Database.getDatabase().createConnection();
        //initiates the server with the messageHandlers, the DataBasePackage.DataBase connection and the logger
        addMessageHandler();
        //todo: database connection + logger
    }

    private static void addMessageHandler() {

        //add all messsagehandler
        MessageHandlerFactory.addHandler("Handlers.GameMessageHandler"); //todo see if i can keep
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
