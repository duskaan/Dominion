package Server;

import Handlers.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class DominionServer extends Thread {

	ServerSocket serverSocket;
	/*starts the server with the corresponding serverSocket port for clients to address to build up a connection
		the executors thread pool creates a pool of all the threads of this server. These threads are created in the next step*/
	@Override
	public void start() {
		try {
			final int PORT = 9000;
			serverSocket = new ServerSocket(PORT);
			LogHandling.logOnFile(Level.INFO, "ServerSocket created on address: " + serverSocket.getInetAddress());
			acceptConnection(serverSocket);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void acceptConnection(ServerSocket serverSocket) throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		while (true) {
			Socket clientSocket = serverSocket.accept();
			LogHandling.logOnFile(Level.INFO, "Client connection accepted: " + clientSocket.getInetAddress());
			Player player = new Player(clientSocket);
			MessageHandler.clientList.add(player);
			executor.submit(player);
		}
	}
}
