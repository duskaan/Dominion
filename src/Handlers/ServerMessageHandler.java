package Handlers;

import Controllers.RegisterController;
import Server.LogHandling;

import java.util.logging.Level;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.SERVER.toString();
    private ServerLoginMessageHandler observedLoginMessageHandler;
    private ServerRegisterMessageHandler observedRegisterMessageHandler;
    private MessageHandler superHandler;

    public ServerMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAIN_HANDLER_INDEX);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public ServerMessageHandler() {
    }

    //why cant i have this method just locally in the super class because all subHandlers are responsible themselves.
    @Override
    public void handleMessage(String msgIn, MessageHandler superHandler) throws UnknownFormatException {
        this.superHandler= superHandler;
        LogHandling.logOnFile(Level.INFO,msgIn);
        //LogHandling.closeResources();
        String subHandler = splitMessage(msgIn, SUB_HANDLER_INDEX);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        //checkForModels(msgIn);
        handler.handleMessage(msgIn,this);
    }


    @Override //explain
    public void write(String message) {
        String tempMessage = addDelimiter(message);
        String newMessage = CLASSNAME + tempMessage;
        superHandler.write(newMessage);
    }

    private void checkForModels(String msgIn) { //use an indiv int as counter if first time or not
        if (splitMessage(msgIn, SUB_HANDLER_INDEX).equalsIgnoreCase("LOGIN") && observedLoginMessageHandler == null) {
            observedLoginMessageHandler = new ServerLoginMessageHandler();

        }
        System.out.println(splitMessage(msgIn, SUB_HANDLER_INDEX));
        if (splitMessage(msgIn, SUB_HANDLER_INDEX).equals("REGISTER") && observedRegisterMessageHandler == null) {
            observedRegisterMessageHandler = new ServerRegisterMessageHandler();
            RegisterController registerModel = new RegisterController();
            observedRegisterMessageHandler.addObserver(registerModel);

        }
    }

}
