/**
 * Created by Tim on 13.09.2017.
 */
public class ServerRegisterMessageHandler extends MessageHandler{
    private final String CLASSNAME = ServerMessageType.REGISTER.toString();

    public ServerRegisterMessageHandler(String message) throws UnknownFormatException {
        if(!CLASSNAME.equals(message)){
            throw new UnknownFormatException(message);
        }
    }

    @Override
    public String handleMsg(String msgIn) throws UnknownFormatException {
        String returnMessage=null;

        if(register(msgIn)) {//if register successful
            returnMessage = "register successful";
            //send message to okMessageHandler with first altering the original msgIn and then handler.HandleMessage...
        }else{ //if register failed
            returnMessage= "Username already in use";
        }

        return returnMessage;
    }
    public boolean register(String msgIn){
        boolean registerSuccessful = false;

        String userName= splitMessage(msgIn,4);
        String password = splitMessage(msgIn, 5);
        DataBase dataBase = new DataBase();

        registerSuccessful= dataBase.insert(userName,password); //only registers if the name does not exist


        return registerSuccessful;
    }
}
