package Controllers;

import Handlers.MessageHandler;
import Server.LogHandling;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Created by Tim on 24.09.2017.
 */
public class WriteOtherClients {
    public ArrayList<MessageHandler> waitingList;
    public ArrayList<MessageHandler> playingList;
    public ArrayList<MessageHandler> lobbyList;
    public static MessageHandler messageHandler;

    public WriteOtherClients(MessageHandler messageHandler) { //todo do i need to create a singleton?
        this.messageHandler = messageHandler;
    }

    public void addWaitingClient() {

        if (waitingList == null) {
            waitingList = new ArrayList<>();
            //waiting = (ArrayList) Collections.synchronizedList(waitingList);;
            LogHandling.logOnFile(Level.INFO, "waitingList is initiated");
        }
        waitingList.add(messageHandler);
        System.out.println("Add waiting client");
        LogHandling.logOnFile(Level.INFO, "messageHandler is added");
    }

    public void addLobbyClient() { //todo if i add a client
        if (lobbyList == null) {
            lobbyList = new ArrayList<>();
            //waiting = (ArrayList) Collections.synchronizedList(waitingList);;
            LogHandling.logOnFile(Level.INFO, "lobbyList is initiated");
        }
        lobbyList.add(messageHandler);
        remove(waitingList, messageHandler);
    }

    public void addPlayingClient() {
        if (playingList == null) {
            playingList = new ArrayList<>();
            LogHandling.logOnFile(Level.INFO, "playingList is initiated");
        }
        playingList.add(messageHandler);
        remove(lobbyList, messageHandler);
    }

    public void remove(ArrayList arrayList, MessageHandler messageHandler) { //todo return arraylist?
        Iterator it = arrayList.iterator();
        while (it.hasNext()) { //todo write safe remove method
            /*if(it.next().) {
                it.remove();
            }*/
        }
    }

    public void writeToGameClients(String message, String[] userNames) {
        //String[] splitMessage = messageHandler.splitMessage(message);

        Iterator iterator = playingList.iterator();
        while(iterator.hasNext()) {
            MessageHandler messageHandlerFromList = (MessageHandler) iterator.next(); //does this work?
            int i = 0;
            while(i<=userNames.length){
                /*if (messageHandlerFromList.getUserName() == userNames[i]) {
                    messageHandlerFromList.write(message);
                }*/
                i++;
            }
        }
    }

    public void writeToLobbyClients(String message) {
        Iterator iterator = lobbyList.iterator();
        if (iterator.hasNext()) {
            MessageHandler messageHandler = (MessageHandler) iterator.next();
            messageHandler.write(message);

        }
    }

    public static MessageHandler getMessageHandler() {
        return messageHandler;
    }
}

