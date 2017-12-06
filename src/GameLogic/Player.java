package GameLogic;

import GameLogic.cards.CardName;

import java.util.Hashtable;

public class Player {

    //player information
    private int ID;
    private String playerName;
    private int victoryPoints = 0;
    private int coins = 0;
    //private int tempCoins = 0;
    private int actions = 1;
    private int buy = 1;

    private Hashtable<CardName,Integer> playerDeck;
    private Hashtable<CardName,Integer> discardDeck;
    private Hashtable<CardName,Integer> handDeck;


    //constructor Player class
    Player(int ID, String playerName) {
        playerDeck = new Hashtable<>();
        discardDeck = new Hashtable<>();
        handDeck = new Hashtable<>();
        this.playerName = playerName;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return playerName;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public void endTurn(){
        buy = 1;
        actions = 1;
    }

    void setCard(CardName cardName, int amount) {
        playerDeck.put(cardName,amount);
    }

    public Hashtable<CardName, Integer> getPlayerDeck() {
        return playerDeck;
    }



    public Hashtable<CardName, Integer> getDiscardDeck() {
        return discardDeck;
    }

    public Hashtable<CardName, Integer> getHandDeck() {
        return handDeck;
    }


    public void setHandDeck(CardName cardName, int i) {
        Hashtable<CardName, Integer> handDeck = new Hashtable<>(); //TODO: Ersetzt das nicht jede Karte immer wieder mit der vorherigen?
        handDeck.put(cardName,i);
        this.handDeck = handDeck;
    }

    public void setBuyingPoints(int buyingPoints) { this.buy += buy; }
    public void setActionPoints(int actionPoints) { this.actions += actionPoints; }
    public void setVictoryPoints(int victoryPoints) { this.victoryPoints += victoryPoints; }
    public void setCoins(int coins) { this.coins += coins; }
    //public void setTempCoins(int tempCoins) { this.tempCoins = tempCoins; }


    public int getBuy() { return buy; }



}
