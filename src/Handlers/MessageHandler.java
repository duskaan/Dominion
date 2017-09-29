package Handlers;

import Models.ReadingThread;
import Models.WriteOtherClients;
import Server.LogHandling;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandler extends Observable {
    public final int MAINHANDLER = 0;
    public final int SUBHANDLER = 1;
    static Socket clientSocket;
    private boolean running = true;
    private BufferedWriter output;
    private BufferedReader input;
    private static String userName;
    private WriteOtherClients writeOtherClients;
    private ReadingThread readingThread;


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
        if(output==null){
            openResources();
        }
        try {
            System.out.println("writing " + clientSocket);
            output.write(outMessage + clientSocket + "\n"); //todo send message to other clients of the game, how do i get the info here?
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
            System.out.println(clientSocket);
            LogHandling.logOnFile(Level.INFO, "Resources are open");
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); //TODO: messages to client

            //send connected message to client open login window
            //create a map of player


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*the OutputListener is to react if the value changes, therefore it can send a message to the client
    * with every update from the value outMessage, this method is called.       */
    public void read() {
        new Thread(() -> { //todo after this there is no clientsocket anymore so i cant write
            System.out.println("Here the clientSocket is still there" + clientSocket);
            System.out.println("Starts listening");
            LogHandling.logOnFile(Level.INFO, "new reading-thread is initiated");
            while (running) {
                System.out.println("is listening");
                tryReadMessage(input);

            }

        }).start();
    }

    private void tryReadMessage(BufferedReader input) {
        String message;
        System.out.println("try to read message");
        System.out.println(input);
        try {
            while ((message = input.readLine()) != null) {
                System.out.println("received first message");
                System.out.println(message);
                MessageHandler handler = MessageHandlerFactory.getMessageHandler(message); // todo create new handler without clientsocket..
                // solved dont i need to just send in the splitted msg?
                /*String hostAndPort = clientSocket.getInetAddress().getHostAddress();
                hostAndPort= hostAndPort+":"+clientSocket.getPort();
                message = handler.addClientSocket(hostAndPort, message);*/
                handler.setClientSocket(clientSocket);
                handler.handleMsg(message);

            }
            System.out.println("get out of it already");
        } catch (IOException e) {
            closeResources();
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
        LogHandling.logOnFile(Level.INFO, "WriteOtherClients is added");
        //LogHandling.closeResources();
    }

    public WriteOtherClients getWriteOtherClients() {
        LogHandling.logOnFile(Level.INFO, "MessageHandler is in the waitingList");
        return writeOtherClients;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
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


