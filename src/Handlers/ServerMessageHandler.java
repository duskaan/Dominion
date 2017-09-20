package Handlers;

import Models.LoginModel;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.SERVER.toString();

    public ServerMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAINHANDLER);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    //why cant i have this method just locally in the super class because all subHandlers are responsible themselves.
    @Override
    public void handleMsg(String msgIn) throws UnknownFormatException {
        String subHandler = splitMessage(msgIn, SUBHANDLER);
        MessageHandler handler = MessageHandlerFactory.getMessageHandler(subHandler);
        checkForModels(msgIn);
        handler.handleMsg(msgIn);
    }


    @Override //explain
    public void write(String outMessage) {
        String tempMessage = addDelimiter(outMessage);
        String newMessage = CLASSNAME + tempMessage;
        super.write(newMessage);
    }
    private void checkForModels(String msgIn) {
        if(splitMessage(msgIn, SUBHANDLER)=="ServerLoginMessageHandler"){
            ServerLoginMessageHandler observedLoginMessageHandler = new ServerLoginMessageHandler();
            LoginModel loginModel = new LoginModel();
            observedLoginMessageHandler.addObserver(loginModel);
        }
    }

}
