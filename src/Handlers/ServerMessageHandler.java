package Handlers;

import Models.LoginModel;
import Models.RegisterModel;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerMessageHandler extends MessageHandler {

    private final String CLASSNAME = MessageType.SERVER.toString();
    ServerLoginMessageHandler observedLoginMessageHandler;
    ServerRegisterMessageHandler observedRegisterMessageHandler;

    public ServerMessageHandler(String message) throws UnknownFormatException {
        String mainHandler = splitMessage(message, MAINHANDLER);
        if (!CLASSNAME.equals(mainHandler)) {
            throw new UnknownFormatException(message);
        }
    }

    public ServerMessageHandler() {

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
        //todo writeToLobby?
    }

    private void checkForModels(String msgIn) { //use an indiv int as counter if first tiem or not
        if (splitMessage(msgIn, SUBHANDLER) == "LOGIN" && observedLoginMessageHandler == null) {
            observedLoginMessageHandler = new ServerLoginMessageHandler();
            LoginModel loginModel = new LoginModel();
            observedLoginMessageHandler.addObserver(loginModel);

        }
        if (splitMessage(msgIn, SUBHANDLER) == "REGISTER" && observedRegisterMessageHandler == null) {
            observedRegisterMessageHandler = new ServerRegisterMessageHandler();
            RegisterModel registerModel = new RegisterModel();
            observedLoginMessageHandler.addObserver(registerModel);

        }
    }

}
