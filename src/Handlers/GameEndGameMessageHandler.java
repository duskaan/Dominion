package Handlers;

/**
 * Created by Tim on 23.09.2017.
 */
public class GameEndGameMessageHandler extends GameMessageHandler {
    private final String CLASSNAME = GameMessageType.ENDGAME.toString();
    private String message = null;
    private MessageHandler superHandler;
    //List<Observer> observers;


    public GameEndGameMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }

    public GameEndGameMessageHandler() {

    }

    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        superHandler.write(newMessage);
    }

    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler = superHandler;
        message = msgIn;
        setChanged();
        notifyObservers(this);
        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model


    }

    public String getMessage() {
        return message;
    }
}


