package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tim on 23.08.2017.
 */
public class DominionServer extends Thread{
    private static final int PORT= 9000;

    /*starts the server with the corresponding serversocket port for clients to address to build up a connection
    the executors thread pool creates a pool of all the threads of this server. these threads are created in the next step*/
    @Override
    public void start() {
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            while(true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientThread(clientSocket));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
