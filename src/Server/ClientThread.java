package Server;

import Handlers.MessageHandlerFactory;

import java.net.Socket;
import java.util.*;

/**
 * Created by Tim on 23.08.2017.
 */
public class ClientThread implements Runnable {
    private static Socket clientSocket;

    private static Map<Socket, String> players;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    /*opens the readers and writers as well as an outputListener */
    @Override
    public void run() {

        MessageHandlerFactory.createCommunicate(clientSocket);
}


    public static void addPlayer(String userName){
        if(players==null){
            players = Collections.synchronizedMap(new HashMap<Socket, String>());
        }
        players.put(clientSocket,userName);


    }
}
