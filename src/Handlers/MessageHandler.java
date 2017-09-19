package Handlers;

/**
 * Created by Tim on 23.08.2017.
 */
public abstract class MessageHandler {
    public final int clientSocketIndex = 0;
    public final int mainHandlerIndex = 1;
    public final int subHandlerIndex = 2;
    private final String splitter = "@"; //todo set this one with the teammates

    public abstract String handleMsg(String msgIn) throws UnknownFormatException;

    //returns a string array with the message split at the specific regex
    public String[] splitMessage(String message) {
        return message.split(splitter);
    }

    public String splitMessage(String message, int tokenIndex) {
        return splitMessage(message)[tokenIndex];
    }

    public String alterMessage(String message, String newValue, int tokenIndex) {
        String[] msgIn = splitMessage(message);
        msgIn[tokenIndex] = newValue;
        message = String.join(splitter, msgIn);
        return message;
    }


    public String addClientSocket(String clientSocket, String message) {
        message= clientSocket+splitter+message;  //todo clientSocket in the msg or not?
        return message; //todo make a map of clientsockets and so on
    }
}


