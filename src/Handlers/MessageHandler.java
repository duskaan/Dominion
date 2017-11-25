package Handlers;

import Server.Player;
import Server.LogHandling;
import org.jetbrains.annotations.Contract;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import java.util.Observable;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandler extends Observable {
	public static ArrayList<Player> clientList = new ArrayList<>();
	private Socket socket;


	private boolean running = true;
	private BufferedWriter writer;
	private BufferedReader reader;

	public final int MAIN_HANDLER_INDEX = 0;
	public final int SUB_HANDLER_INDEX = 1;

	private static final String DELIMITER = "@";

	public MessageHandler(){}
	public MessageHandler(Socket socket){
		this.socket = socket;
	}


	public void openResources() {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void read() {
		new Thread(() -> {
			while (running) {
				tryReadMessage(reader);
			}
		}).start();
	}

	private void tryReadMessage(BufferedReader input) {
		String message;
		try {
			while ((message = input.readLine()) != null) {
				MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
				handler.handleMessage(message,this);

			}
		} catch (IOException e) {
			closeResources();
			e.printStackTrace();
		} catch (UnknownFormatException e) {
			LogHandling.logOnFile(Level.INFO, e.getMessage());
		}
	}

	public void write(String message) { //todo set new
		/*if (writer == null || reader == null) {
			openResources();
		}*/
		try {
			LogHandling.logOnFile(Level.INFO, message+socket);
			writer.write(message+" " + socket + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeAll(){
		//todo write all method
	}

	@Contract(pure = true)
	public static String addDelimiter(String message) {
		return DELIMITER + message;
	}

	public String splitMessage(String message, int tokenIndex) {
		String[] tokens = message.split(DELIMITER);
		return tokens[tokenIndex];
	}

	public void handleMessage(String msgIn, MessageHandler handler) throws UnknownFormatException {
	}

	private void closeResources() {
		try {
			reader.close();
			writer.close();
			socket.close();
			LogHandling.closeResources();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


