package Handlers;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerLoginMessageHandler extends ServerMessageHandler  {
    private final String CLASSNAME = ServerMessageType.LOGIN.toString();
    private String message = null;
    private MessageHandler superHandler;
    //List<Observer> observers;


    public ServerLoginMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerLoginMessageHandler(){
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
        setChanged();
        notifyObservers(msgIn);
        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model


    }

    public String getMessage(){
        return message;
    }

    @Override
    public String splitMessage(String message, int tokenIndex) {
        return super.splitMessage(message, tokenIndex);
    }
}
