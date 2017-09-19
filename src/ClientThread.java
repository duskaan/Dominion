import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.*;
import java.net.Socket;

/**
 * Created by Tim on 23.08.2017.
 */
public class ClientThread implements Runnable {
    private Socket clientSocket;
    private boolean running;
    private BufferedWriter output;
    private BufferedReader input;
    private volatile StringProperty outMessage;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    /*opens the readers and writers as well as an outputListener */
    @Override
    public void run() {
        openResources();
}

    private void openResources() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); //TODO: messages to client
            addOutputListener();
            read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*the OutputListener is to react if the value changes, therefore it can send a message to the client
    * with every update from the value outMessage, this method is called.       */
    private void addOutputListener() {
        outMessage = new SimpleStringProperty("");
        outMessage.addListener((observable, oldValue, newValue) -> sendMessageToClient(newValue));
    }

    private void sendMessageToClient(String outMessage) {
        try {
            output.write(outMessage); //todo send message to other clients of the game, how do i get the info here?
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                String hostAndPort = clientSocket.getInetAddress().getHostAddress();
                hostAndPort= hostAndPort+":"+clientSocket.getPort();
                message = handler.addClientSocket(hostAndPort, message);
                String returnMessage = handler.handleMsg(message);
                outMessage.setValue(returnMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnknownFormatException e) {
            //TODO: Log this message
            e.getMessage();
        }
    }

    private void closeResources() {
        try {
            input.close();
            output.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
