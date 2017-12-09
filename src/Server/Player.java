package Server;

import Handlers.MessageHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Tim on 23.08.2017.
 */
public class Player implements Runnable {
    private Socket socket;
    public Status status;
    public String gameName;
    public String playerName;
    private MessageHandler messageHandler;


    public Player(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        messageHandler = new MessageHandler(socket);
        messageHandler.openResources();
        messageHandler.read();

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public String getGameName(){
        return gameName;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName=playerName;
    }


    @Override
    public String toString() {
        return playerName;
    }
}
