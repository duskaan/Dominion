/**
 * Created by Tim on 12.09.2017.
 */
public class ServerMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.SERVER.toString();

    public ServerMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, mainHandlerIndex);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }
    //why cant i have this method just locally in the super class because all subHandlers are responsible themselves.
    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage;
        String subHandler = splitMessage(msgIn, subHandlerIndex);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        returnMessage =handler.handleMsg(msgIn);

        return returnMessage;
    }
}
