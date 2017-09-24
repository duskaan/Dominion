package Server;

import Database.Database;
import Handlers.MessageHandlerFactory;

import java.util.logging.Level;

/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String[] args) {

        initServer();
        LogHandling.logOnFile(Level.INFO, "MessageHandler are added");
        //Application.launch(ServerGUI.class, args);
        new DominionServer().start();

        LogHandling.closeResources();
    }

    private static void initServer() {

        LogHandling.logOnFile(Level.INFO, "Server.Server application starts now");

        //todo see that it gets called

        Database.getDatabase().createConnection();
        //initiates the server with the messageHandlers, the Database.DataBase connection and the logger
        addMessageHandler();

    }

    private static void addMessageHandler() {

        //add all messsagehandler
        MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
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
