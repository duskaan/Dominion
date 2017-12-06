package Server;

import Handlers.MessageHandlerFactory;
import Database.*;

import java.util.logging.Level;

/**
 * Created by Tim on 21.08.2017.
 */
public class Server {


    public static void main(String[] args) {

        initServer();
        //Application.launch(ServerGUI.class, args);
        new DominionServer().start();
        LogHandling.closeResources();
    }

    private static void initServer() {
        addMessageHandler();
        createDatabaseConnection();

    }

    private static void addMessageHandler() {
        LogHandling.logOnFile(Level.INFO, "Adding MessageHandlers");

        MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerConnectedMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerDisconnectMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerLoginMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerRegisterMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerNewGameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerLobbyMessageHandler");

        MessageHandlerFactory.addHandler("Handlers.GameStartGameMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.ServerChatMessageHandler");
        MessageHandlerFactory.addHandler("Handlers.GameEndGameMessageHandler");
        //MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");
        //MessageHandlerFactory.addHandler("Handlers.GameMessageHandler");

        LogHandling.logOnFile(Level.INFO, "All MessageHandlers added successfully");
    }

    private static void createDatabaseConnection() {
        LogHandling.logOnFile(Level.INFO, "Creating Database connection");
        Database.getDatabase().initDatabase();
        Database.getDatabase().createConnection();
        test();
        LogHandling.logOnFile(Level.INFO, "Database connection created successfully");
    }
    public static void test(){
        //System.out.println(Database.getDatabase().insert("Tim", "1234"));
        //System.out.println(Database.getDatabase().login("Tim", "1234"));
        //Database.getDatabase().updateAfterGame("Tim",10,true);
        //System.out.println(Database.getDatabase().getTopFive());
    }
}
