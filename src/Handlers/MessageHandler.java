package Handlers;

import GameLogic.Game;
import Server.Player;
import Server.LogHandling;

import java.io.*;
import java.net.Socket;
import java.util.*;

import java.util.logging.Level;

/**
 * Created by Tim on 23.08.2017.
 */
public class MessageHandler implements Observer {
    public static ArrayList<Player> lobbyList = new ArrayList<>();
    public static HashMap<Player, Game> gameList = new HashMap<>();
    public static HashMap<Integer, Player> socketPlayerHashMap = new HashMap<>();

    private Socket socket;


    private boolean running = true;
    private BufferedWriter writer;
    private BufferedReader reader;

    public final int MAIN_HANDLER_INDEX = 0;
    public final int SUB_HANDLER_INDEX = 1;

    private static final String DELIMITER = "@";

    public MessageHandler() {
    }

    public MessageHandler(Socket socket) {
        this.socket = socket;
        LogHandling.logOnFile(Level.INFO, "MessageHandler is created for: " + socket);
    }

    //@Tim
    //buffered writer and reader are opened
    public void openResources() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            LogHandling.logOnFile(Level.INFO, "Resources are opened");
        } catch (IOException e) {
            LogHandling.logOnFile(Level.WARNING, e.getMessage());
        }
    }

    //@Tim
    //a new thread, which starts reading as long as the server is running
    public void read() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                tryReadMessage(reader);
            }
        });

        thread1.start();
    }

    //@Tim
    //for every Message the correct MessageHandler is Server or Client
    //the message is sent into the corresponding Handler
    private void tryReadMessage(BufferedReader input) {
        String message;
        try {
            while ((message = input.readLine()) != null) {
                LogHandling.logOnFile(Level.INFO, "incoming message: " + message);
                MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
                handler.handleMessage(message, this);

            }
        } catch (IOException e) {
            closeResources();
            LogHandling.logOnFile(Level.WARNING, e.getMessage());
        } catch (UnknownFormatException e) {
            LogHandling.logOnFile(Level.WARNING, e.getMessage());
        }
    }

    public void write(String message, Player player) {

    }

    //@Tim
    //gets the player from the map and and checks if it is in a game or in the lobby
    //if it is in the lobby list it sends out the message to all clients, if privateMessage is false
    //else it sends it only to the player
    //if it is not in the lobbylist it sends out the message to all clients in the game
    public void write(String message, Boolean privateMessage) {
        LogHandling.logOnFile(Level.INFO, "The message is: " + message + " It is a private message: " + privateMessage);
        Player player = socketPlayerHashMap.get(socket.getPort());
        LogHandling.logOnFile(Level.INFO, "SocketPlayerHashMap: " + socketPlayerHashMap.toString());
        if (!privateMessage) {
            if (lobbyList.contains(player)) {
                LogHandling.logOnFile(Level.INFO, "LobbyList: " + lobbyList.toString());
                writeToList(lobbyList, message);
            } else {
                Game game = gameList.get(player);
                ArrayList<Player> playerInGame = getKeyByValue(gameList, game);
                writeToList(playerInGame, message);
            }
        }
        if (privateMessage) {
            try {
                ArrayList<Player> writeTo = new ArrayList<>();
                writeTo.add(player);
                writeToList(writeTo, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //@Tim
    //takes the list and sends the message to all clients in the list.
    private void writeToList(ArrayList<Player> list, String message) {
        LogHandling.logOnFile(Level.INFO, list.toString());
        for (int i = 0; list.size() > i; i++) {

            try {

                LogHandling.logOnFile(Level.INFO, "Message: " + message + " sent to " + list.get(i));
                BufferedWriter tempWriter = list.get(i).getMessageHandler().getWriter(); //i everytime get the last writer
                tempWriter.write(message + "\n");
                tempWriter.flush();
            } catch (IOException e) {
                LogHandling.logOnFile(Level.WARNING, "Resource: " + list.get(i).playerName + " is closed");
            }
        }
    }

    //@Tim
    //adds a delimiter to the message. Delimiter is @
    public static String addDelimiter(String message) {
        return DELIMITER + message;
    }

    //@Tim
    //splits the message with the delimiter and returns the messagepart at that index.
    public String splitMessage(String message, int tokenIndex) {
        String[] tokens = message.split(DELIMITER);
        return tokens[tokenIndex];
    }

    public void handleMessage(String msgIn, MessageHandler handler) throws UnknownFormatException {
    }
    //@Tim
    //closes the writer, reader, socket and Logger
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

    public Socket getClientSocket() {
        return socket;
    }

    public BufferedWriter getWriter() {
        return this.writer;
    }

    //@Tim
    //returns an ArrayList with the Keys(player) from the game
    public static ArrayList<Player> getKeyByValue(HashMap<Player, Game> hashMap, Game game) {
        ArrayList<Player> players = new ArrayList<>();
        for (Map.Entry<Player, Game> entry : hashMap.entrySet()) {
            if (entry.getValue().equals(game)) {
                players.add(entry.getKey());

            }
        }
        return players;
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    //@Tim
    //deletes the player from the lobby list, that the client does not receive lobby messages
    public static void removeFromLobbyList(String... playerArray) {
        Player player;
        Iterator<Player> iterator = lobbyList.iterator();
        while (iterator.hasNext()) {
            player = iterator.next();
            for (int i = 0; playerArray.length < i; i++) {
                if (player.getPlayerName().equalsIgnoreCase(playerArray[i])) {
                    iterator.remove();
                }
            }
        }

    }

    //@Tim
    //adds the game and the player to a map. this map is needed to write all players of one game
    public static void addToGameMap(ArrayList<Player> players, Game game) {
        for (Player player : players) {
            gameList.put(player, game);
        }
    }
}


