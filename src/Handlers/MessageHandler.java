package Handlers;

import Models.WriteOtherClients;
import Server.ClientThread;
import Server.LogHandling;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandler extends Observable {
    public final int MAINHANDLER = 0;
    public final int SUBHANDLER = 1;
    private Socket clientSocket;
    private boolean running = true;
    private BufferedWriter output;
    private BufferedReader input;
    private static String userName;
    private WriteOtherClients writeOtherClients;


    private static final String DELIMITER = "@"; //todo set this one with the teammates

    public MessageHandler() {

    }

    public MessageHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handleMsg(String msgIn) throws UnknownFormatException {

    }


    //returns a string array with the message split at the specific regex
    public String[] splitMessage(String message) {
        return message.split(DELIMITER);
    }

    public String splitMessage(String message, int tokenIndex) {
        return splitMessage(message)[tokenIndex];
    }


    public void write(String outMessage) {
        try {
            output.write(outMessage); //todo send message to other clients of the game, how do i get the info here?
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addDelimiter(String message) {
        String newMessage = DELIMITER + message;
        return newMessage;
    }


    public void openResources() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); //TODO: messages to client

            //send connected message to client open login window
            //create a map of player

            read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*the OutputListener is to react if the value changes, therefore it can send a message to the client
    * with every update from the value outMessage, this method is called.       */

    private void read(BufferedReader input) {
        new Thread(() -> {
            while (running) {
                tryReadMessage(input);
            }
            closeResources();
        }).start();
    }

    //TODO: create or use exception to turn the boolean running into false, so that the thread stops.
    private void tryReadMessage(BufferedReader input) {
        String message;
        try {
            while ((message = input.readLine()) != null) {
                MessageHandler handler = MessageHandlerFactory.getMessageHandler(message); // solved dont i need to just send in the splitted msg?
                /*String hostAndPort = clientSocket.getInetAddress().getHostAddress();
                hostAndPort= hostAndPort+":"+clientSocket.getPort();
                message = handler.addClientSocket(hostAndPort, message);*/
                handler.handleMsg(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnknownFormatException e) {
            LogHandling.logOnFile(Level.INFO, e.getMessage());
            //TODO: Log this message
            e.getMessage();
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setWriteOtherClients(MessageHandler messageHandler) { //todo geht das?
        writeOtherClients = new WriteOtherClients(messageHandler);
    }

    public WriteOtherClients getWriteOtherClients() {
        return writeOtherClients;
    }


    private void closeResources() {
        try {
            input.close();
            output.close();
            clientSocket.close();
            LogHandling.closeResources();
            // todo close Database connection?

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


