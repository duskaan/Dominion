package Handlers;

/**
 * Created by Tim on 23.09.2017.
 */
public class GameChatMessageHandler extends GameMessageHandler{
    private final String CLASSNAME = GameMessageType.CHAT.toString();
    private String message = null;
    //List<Observer> observers;


    public GameChatMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameChatMessageHandler() {

    }

    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }

    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        message = msgIn;
        setChanged();
        notifyObservers(this);
        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model


    }

    public String getMessage() {
        return message;
    }
}


