package Models;

import Handlers.MessageHandler;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tim on 24.09.2017.
 */
public class WriteOtherClients {
    public ArrayList waiting; //todo is the arraylist saved across the different writeotherclients instances?
    public ArrayList playing;
    public ArrayList lobby;
    public static MessageHandler messageHandler;

    public WriteOtherClients(MessageHandler messageHandler) { //todo do i need to create a singleton?
        this.messageHandler = messageHandler;
    }
    public void addWaitingClient() {
        if (waiting == null) {
            waiting = (ArrayList) Collections.synchronizedList(new ArrayList<MessageHandler>());
        }
        waiting.add(messageHandler);
    }

    public void addLobbyClient() { //todo if i add a client
        if (lobby == null) {
            lobby = (ArrayList) Collections.synchronizedList(new ArrayList<MessageHandler>());
        }
        lobby.add(messageHandler);
        remove(waiting, messageHandler);
    }

    public void addPlayingClient() {
        if (playing == null) {
            playing = (ArrayList) Collections.synchronizedList(new ArrayList<MessageHandler>());
        }
        playing.add(messageHandler);
        remove(lobby, messageHandler);
    }

    public void remove(ArrayList arrayList, MessageHandler messageHandler) { //todo return arraylist?
        Iterator it = arrayList.iterator();
        while (it.hasNext()) { //todo write safe remove method
            /*if(it.next().) {
                it.remove();
            }*/
        }
    }

    public void writeToGameClients(String message) {
        String[] splitMessage = messageHandler.splitMessage(message);
        String userName = splitMessage[4];
        Iterator iterator = playing.iterator();
        if (iterator.hasNext()) {
            MessageHandler messageHandler = (MessageHandler) iterator.next();
            if (messageHandler.getUserName() == userName) {
                messageHandler.write(message);
            }
        }
    }

    public void writeToLobbyClients(String message) {
        Iterator iterator = lobby.iterator();
        if (iterator.hasNext()) {
            MessageHandler messageHandler = (MessageHandler) iterator.next();
            messageHandler.write(message);

        }
    }
    public static MessageHandler getMessageHandler(){
        return messageHandler;
    }
}

