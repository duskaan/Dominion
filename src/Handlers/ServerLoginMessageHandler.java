package Handlers;
import DataBasePackage.DataBase;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerLoginMessageHandler extends MessageHandler{
    private final String CLASSNAME = ServerMessageType.LOGIN.toString();

    public ServerLoginMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage=null;

        if(loginCheck(msgIn)) {//if successful
            String message = alterMessage(msgIn,"okMessageHandler",3);
            MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
            returnMessage= handler.handleMsg(message);
            //send message to okMessageHandler with first altering the original msgIn and then handler.HandleMessage...
        }else{ //if login failed
            String message = alterMessage(msgIn,"failedMessageHandler",3);
            MessageHandler handler = MessageHandlerFactory.getMessageHandler(message);
            returnMessage= handler.handleMsg(message);
            //send message to failedMessageHandler
        }

        return returnMessage;
    }
    public boolean loginCheck(String msgIn){
        boolean loginSuccessful = false;
        int userNameIndex = 0;
        String userName= splitMessage(msgIn,4);
        String password = splitMessage(msgIn, 5);
        DataBase dataBase = new DataBase();
        loginSuccessful = dataBase.login(userName, password);
        //todo create player+ socket map
        return loginSuccessful;
    }
}

