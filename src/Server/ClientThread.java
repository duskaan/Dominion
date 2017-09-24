package Server;

import Handlers.MessageHandlerFactory;

import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class ClientThread implements Runnable {
    private static Socket clientSocket;



    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        LogHandling.logOnFile(Level.INFO, "A new Client is connected to the Server");

    }

    /*opens the readers and writers as well as an outputListener */
    @Override
    public void run() {
        LogHandling.closeResources();
        MessageHandlerFactory.createCommunicate(clientSocket);
    }

}
