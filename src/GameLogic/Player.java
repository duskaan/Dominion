package GameLogic;

import GameLogic.cards.CardName;

import java.util.ArrayList;
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

    private Hashtable<CardName, Integer> playerDeck;
    private Hashtable<CardName, Integer> discardDeck;
    private Hashtable<CardName, Integer> handDeck;
    private ArrayList<String> playedDeck;


    //constructor Player class
    Player(int ID, String playerName) {
        playerDeck = new Hashtable<>();
        discardDeck = new Hashtable<>();
        handDeck = new Hashtable<>();
        playedDeck = new ArrayList<>();
        this.playerName = playerName;
        this.ID = ID;
        fillList(playerDeck);
        fillList(discardDeck);
        fillList(handDeck);
    }

    public void fillList(Hashtable<CardName, Integer> list) {
        list.put(CardName.gold, 0);
        list.put(CardName.silver, 0);
        list.put(CardName.copper, 0);
        list.put(CardName.village, 0);
        list.put(CardName.woodcutter, 0);
        list.put(CardName.workshop, 0);
        list.put(CardName.smithy, 0);
        list.put(CardName.councilroom, 0);
        list.put(CardName.festival, 0);
        list.put(CardName.witch, 0);
        list.put(CardName.chancellor, 0);
        list.put(CardName.market, 0);
        list.put(CardName.laboratory, 0);
        list.put(CardName.province, 0);
        list.put(CardName.duchy, 0);
        list.put(CardName.estate, 0);
        list.put(CardName.curse, 0);
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

    public void endTurn() {
        buy = 1;
        actions = 1;
        coins = 0;
    }

    void setCard(CardName cardName, int amount) {
        playerDeck.put(cardName, amount);
    }

    int getDeckAmount() {
        int amount = 0;
        for (CardName cardName : playerDeck.keySet()) {
            amount = amount + playerDeck.get(cardName);
        }
        return amount;
    }

    int getDiscardAmount() {
        int amount = 0;
        for (CardName cardName : discardDeck.keySet()) {
            amount = amount + discardDeck.get(cardName);
        }
        return amount;
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
        handDeck.put(cardName, i);
        this.handDeck = handDeck;
    }

    public void setBuyingPoints(int buyingPoints, boolean addition) {
        if (addition) {
            this.buy = buy + buyingPoints;
        } else this.buy = buy - buyingPoints;
    }

    public void setActionPoints(int actionPoints, boolean addition) {
        if (addition) {
            this.actions = actions + actionPoints;
        } else this.actions = actions - actionPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setCoins(int coinValue, boolean addition) {
        if (addition) {
            this.coins = coins + coinValue;
        } else this.coins = coins - coinValue;

    }

    public int getBuy() {
        return buy;
    }

    public int getCoins() {
        return coins;
    }

    public int getActions() {
        return actions;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addToPlayedDeck(String actionCardPlayed) {
        playedDeck.add(actionCardPlayed);
    }
    public ArrayList getPlayedDeck(){
        return playedDeck;
    }
}
