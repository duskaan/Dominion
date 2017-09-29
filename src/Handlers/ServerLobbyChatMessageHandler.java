package Handlers;

/**
 * Created by Tim on 23.09.2017.
 */
public class ServerLobbyChatMessageHandler extends ServerMessageHandler {
    private final String CLASSNAME = ServerMessageType.LOBBYCHAT.toString();
    private String message = null;



    public ServerLobbyChatMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerLobbyChatMessageHandler(){

    }
    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }

    @Override
    public  void handleMsg(String msgIn) throws UnknownFormatException {
        message = msgIn;
        setChanged();
        notifyObservers(this);
        //code with observable and observer -- notify and update() -- send this with it write getMessage Method to return the string to the model


    }

    public String getMessage(){
        return message;
    }


}
