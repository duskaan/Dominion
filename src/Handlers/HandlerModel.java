package Handlers;

import Database.Database;
import Server.LogHandling;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Tim on 05.12.2017.
 */
public class HandlerModel {
    //@Tim
    //returns the GameList
    //if it is empty "gameList is empty" wird zurück gesendet
    public static String gameListMessage() {
        String reply = "GameList/";
        ArrayList<TempGame>list= ServerMessageHandler.gettempGameArrayList();
        if(list.size()==0){
            LogHandling.logOnFile(Level.WARNING, "gameList is empty");
            return null;

        }
        for(int i=0;list.size()>i;i++){
            reply+=list.get(i).getGameName() + "/";
           // reply+=list.get(i).getPlayerList().size()+";";
            //reply+=list.get(i).getMaxPlayer();
        }
        return reply; //structure = GameName; Players in the game; max player number/GameName; Players in the game; max player number

    }
    //@Tim
    //returns the prepared string from the database
    public static String topFiveMessage() {
        Database database = Database.getDatabase();
        return "TOPFIVE@" + database.getTopFive();

    }
    //@Tim
    //tries to login into the database and returns true or false
    public static boolean tryToLogin(String userName, String password) {
        return Database.getDatabase().login(userName, password);
    }


    //@Tim
    //registers in the database and returns true or false depending if it worked or not
    public static boolean register(String userName, String password) {
        return Database.getDatabase().insert(userName, password); //only registers if the name does not exist
    }
}




