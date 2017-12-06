package Handlers;

import Database.Database;

import java.util.ArrayList;

/**
 * Created by Tim on 05.12.2017.
 */
public class HandlerModel {
    public static String gameListMessage() {
        String reply = "@GameList/";
        ArrayList<TempGame>list= ServerMessageHandler.gettempGameArrayList();
        for(int i=0;list.size()>i;i++){
            reply+=list.get(i).getGameName() + ";";
            reply+=list.get(i).getPlayerList().size()+";";
            reply+=list.get(i).getMaxPlayer()+"/";
        }
        return reply; //structure = GameName; Players in the game; max player number/GameName; Players in the game; max player number

    }

    public static String topFiveMessage() {
        Database database = Database.getDatabase();
        return database.getTopFive();

    }

    public static boolean tryToLogin(String userName, String password) {
        return Database.getDatabase().login(userName, password);
    }



    public static boolean register(String userName, String password) {
        return Database.getDatabase().insert(userName, password); //only registers if the name does not exist
    }
}




