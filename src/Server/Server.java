package Server;

import Handlers.MessageHandlerFactory;
import Database.*;
import javafx.application.Application;

import java.util.logging.Level;

/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String[] args) {
        Application.launch(ServerGUI.class, args);
        initServer();

    }

    protected static void initServer() {
        addMessageHandler();
        new DominionServer().start();
        LogHandling.closeResources();

    }

    private static void addMessageHandler() {
        LogHandling.logOnFile(Level.INFO, "Adding MessageHandlers");

        MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerMessageHandler");

        MessageHandlerFactory.addHandler("Handlers.ServerLoginMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerRegisterMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerNewGameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerLobbyMessageHandler");

        MessageHandlerFactory.addHandler("Handlers.GameStartGameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerChatMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.GameEndGameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.GameJoinGameMessageHandler");
        //MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
        //MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");

        LogHandling.logOnFile(Level.INFO, "All MessageHandlers added successfully");
    }

    protected static void createDatabaseConnection(String login, String password) {
        LogHandling.logOnFile(Level.INFO, "Creating Database connection");
        Database.getDatabase().initDatabase();
        Database.getDatabase().createConnection(login, password);
        LogHandling.logOnFile(Level.INFO, "Database connection created successfully");
    }
}
