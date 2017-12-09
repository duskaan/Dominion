package Handlers;

import Server.Player;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tim on 28.09.2017.
 */
public class TempGame {
    private String gameName;
    private int cardsInGame;
    private ArrayList<Player> playerList = new ArrayList<>();
    private Iterator<Player> playerIterator ;
    boolean removed ;


    private int maxPlayer;


    public TempGame(String gameName, int cards, Player creatorPlayer, int maxPlayer) {
        this.gameName = gameName;

        this.playerList.add(creatorPlayer);

        this.cardsInGame = cards;
        this.maxPlayer = maxPlayer;
    }

    public String getGameName() {
        return gameName;
    }

    public int getCardsInGame() {
        return cardsInGame;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }

    public void removePlayer(Player player){
        removed=false;
        playerIterator=playerList.iterator();
        while(playerIterator.hasNext()&&removed==false){
            if(playerIterator.next()==player){
                playerIterator.remove();
                removed =true;
            }
        }
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }
}
