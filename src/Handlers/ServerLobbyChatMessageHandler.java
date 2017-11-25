package Handlers;

/**
 * Created by Tim on 23.09.2017.
 */
public class ServerLobbyChatMessageHandler extends ServerMessageHandler {
    private final String CLASSNAME = ServerMessageType.LOBBYCHAT.toString();
    private String message = null;
    private MessageHandler superHandler;


    public ServerLobbyChatMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerLobbyChatMessageHandler(){

    }
    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        superHandler.write(newMessage);
    }

    @Override
    public  void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;

        write("you got till here");
        //setChanged();
        //notifyObservers(this);
        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model


    }

    public String getMessage(){
        return message;
    }


}
