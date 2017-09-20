package Handlers;

import DataBasePackage.Database;
import Handlers.MessageHandler;
import Handlers.MessageHandlerFactory;
import Handlers.ServerMessageType;
import Handlers.UnknownFormatException;
import Models.LoginModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableStringValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by Tim on 12.09.2017.
 */
public class ServerLoginMessageHandler extends MessageHandler  {
    private final String CLASSNAME = ServerMessageType.LOGIN.toString();
    private String message = null;
    //List<Observer> observers;


    public ServerLoginMessageHandler(String message) throws UnknownFormatException {
        if (!CLASSNAME.equals(message)) {
            throw new UnknownFormatException(message);
        }
    }
    public ServerLoginMessageHandler(){

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
