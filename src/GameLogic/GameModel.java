package GameLogic;

import GameLogic.cards.CardName;
import javafx.beans.property.SimpleStringProperty;
import java.util.*;

public class GameModel {


    private ArrayList<Player> playerList;
    private int turnCount = 0;
    private Hashtable<CardName, Integer> actionCardList;
    private Hashtable<CardName, Integer> coinCardList;
    private Hashtable<CardName, Integer> victoryCardList;
    private Hashtable<CardName, Integer> tempHandDeck;
    private String gameName;
    private int actionCardCount;
    private boolean aiPlayer;
    private String gameResponseMessage;


    GameModel(String gameName, String gameResponseMessage) {
        this.gameName = gameName;
        this.gameResponseMessage = gameResponseMessage;
        playerList = new ArrayList<Player>();
    }

//    public void addPlayer(String playerName) { //TODO MAKE TWO OUT OF THEM ONE FOR 2 player one for 4 players
//        if (playerList == null) {
//            playerList = new ArrayList<>();
//        }
//        int playerID = playerList.size();
//        if (playerID <= playerCount - 1) {
//            //TODO test what happen we try to add more player than player count
//            Player player = new Player(playerID, playerName);
//            playerList.add(player);
//            if (playerID == playerCount - 1) {
//                gameResponseMessage.set("Game is Ready"); //TODO talk to tim about what message protcol to use
//            }
//        } else {
//            gameResponseMessage.set("PlayerList is already full"); //TODO talk to tim about what message protocol to use
//        }
//    }


    void removePlayer(int playerID) {
        if (playerList.size() >= playerID) {
            playerList.remove(playerID);
            for (int i = 0; i <= playerList.size() - 1; i++) {
                playerList.get(i).setID(i);
            }
        }
    }


    void init() {
        initActionCards();
        initCoinCardCount();
        initVictoryCurseCards();
        initPlayers();
    }

    private void initActionCards() { // TODO select all 3coin cards + smithy + festival
        ArrayList<CardName> actionCardNames = new ArrayList<>();
        CardName[] cardNames = CardName.values();
        int numberOfActionCardInEnumList = 10;
        for (int i = 0; i < numberOfActionCardInEnumList; i++) {

            actionCardNames.add(cardNames[i]);
        }
        Collections.shuffle(actionCardNames);

        for (int i = 0; i < actionCardCount; i++) {
            actionCardList.put(actionCardNames.get(i), 10);
        }
    }


    private void initVictoryCurseCards() {
        setVictoryCount(8, 8, 8, 10);
    }

    private void setVictoryCount(int estateCount, int duchyCount, int provinceCount, int curseCount) {
        victoryCardList.put(CardName.estate, estateCount);
        victoryCardList.put(CardName.duchy, duchyCount);
        victoryCardList.put(CardName.province, provinceCount);
        victoryCardList.put(CardName.curse, curseCount);
    }


    private void initCoinCardCount() {
        coinCardList.put(CardName.gold, 30);
        coinCardList.put(CardName.silver, 40);
        coinCardList.put(CardName.copper, 46);
    }


    private void initPlayers() {
        setPlayerInitCardDeck();
    }

    private void setPlayerInitCardDeck() {
        for (Player player : playerList) {
            player.setCard(CardName.estate, 3);
            player.setCard(CardName.copper, 7);
        }
    }

    public void endTurn() {
        turnCount++;
        discardHandDeckToDiscardDeck();
        isGameOver();
        drawCards(5,getCurrentPlayer());
        playerList.get(getCurrentPlayer()).endTurn();
        createMessage();

    }

    //end of each turn discard your HandDeck to discardDeck
    private void discardHandDeckToDiscardDeck() {
        for (CardName cardName : playerList.get(getCurrentPlayer()).getHandDeck().keySet()) {
            if (playerList.get(getCurrentPlayer()).getHandDeck().get(cardName) != 0) {
                int currentCount = playerList.get(getCurrentPlayer()).getHandDeck().get(cardName);
                playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName, currentCount);
                playerList.get(getCurrentPlayer()).getHandDeck().put(cardName, 0);
            }
        }
    }



    private void createMessage() { //TODO TALK WITH TIM

    }

    public boolean isGameOver() {
        int emptyActionCards = 0;

        for (CardName cardName : actionCardList.keySet()) {
            Integer numberOfActionCard = actionCardList.get(cardName);
            if (numberOfActionCard == 0) {
                emptyActionCards++;
            }
        }
        if (emptyActionCards >= 3) {
            calculateVictoryPoints();
            return true;
        }

        Integer numberOfProvinceCard = victoryCardList.get(CardName.province);
        if(numberOfProvinceCard==0){
            calculateVictoryPoints();
            return true;
        }
        return false; //TODO IS THIS OKAY OR DOES IT ALWAYS GIVE BACK FALSE
    }


    public Hashtable<Player,Integer> calculateVictoryPoints() { //TODO maybe return type... hashtable so I could give out that..at moment just stored in the player

        int i = 0;
        Hashtable<Player, Integer> finalPointsList = new Hashtable<>();
        int playerSize = playerList.size();

        while (i <= playerSize) {
            int provinceAmount = playerList.get(i).getPlayerDeck().get(CardName.province);
            int estateAmount = playerList.get(i).getPlayerDeck().get(CardName.estate);
            int duchyAmount = playerList.get(i).getPlayerDeck().get(CardName.duchy);
            int curseAmount = playerList.get(i).getPlayerDeck().get(CardName.curse);

            int finalVictoryPoints = provinceAmount * 6 + duchyAmount * 3 + estateAmount + curseAmount * -1;

            playerList.get(i).setVictoryPoints(finalVictoryPoints);
            i++;
        }
        return finalPointsList;
    }


    //draw card from playerDeck
    private void drawCards(int amountToDraw, int playerIndex) {//TODO secondPlayer
        ArrayList<CardName> cardNames = new ArrayList<>();
        for (CardName cardName : playerList.get(playerIndex).getPlayerDeck().keySet()) {
            cardNames.add(cardName);
        }
        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();

        while (cardsDrawn < amountToDraw) { //TODO FOR SCHLEIFE DAURAUS MACHEN
            cardName = cardNames.get(rand.nextInt(cardNames.size()));

            if (playerList.get(getCurrentPlayer()).getPlayerDeck().get(cardName) != 0) {
                int currentCount = playerList.get(playerIndex).getPlayerDeck().get(cardName);
                playerList.get(playerIndex).getPlayerDeck().put(cardName, currentCount - 1);
                checkifPlayerDeckisEmpty();
                currentCount = playerList.get(playerIndex).getHandDeck().get(cardName);
                playerList.get(playerIndex).getHandDeck().put(cardName, currentCount + 1);
                cardsDrawn++;
            }
        }
    }

    private void checkifPlayerDeckisEmpty() {

        boolean hasCards = false;
        for (CardName cardName : playerList.get(getCurrentPlayer()).getPlayerDeck().keySet()) {
            if (playerList.get(getCurrentPlayer()).getPlayerDeck().get(cardName) > 0) {
                hasCards = true;
            }
        }

        if (!hasCards) discardDecktoPlayerDeck();
    }

    //When the player deck is empty discardDeck will be shuffled nd put into playerDeck
    private void discardDecktoPlayerDeck() { //TODO: get descardDeck HERMAN FRAGEN OB PUT NICHT EIFACH EINS DAZU FèGT
        ArrayList<CardName> cardNames = new ArrayList<>();
        for (CardName cardName : playerList.get(getCurrentPlayer()).getPlayerDeck().keySet()) {
            cardNames.add(cardName);
        }
        CardName cardName = null;

        for (int i = 0; i <= playerList.get(getCurrentPlayer()).getDiscardDeck().size();i++){
            cardName = cardNames.get(i);

            if(playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName) != 0){
                int currentCount = playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
                playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName, 0);
                playerList.get(getCurrentPlayer()).getPlayerDeck().put(cardName, currentCount);
            }
        }

    }

    public void buyCard(CardName cardName) { //TODO buyCard method

        switch (cardName){
            case gold:
                addBuy(-1);
                addCoins(-6);
                cardFromHashTableToDiscardDeck(CardName.gold, coinCardList);
                break;
            case silver:
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.silver, coinCardList);
                break;
            case copper:
                addBuy(-1);
                cardFromHashTableToDiscardDeck(CardName.copper, coinCardList);
                break;
            case province:
                addBuy(-1);
                addCoins(-8);
                cardFromHashTableToDiscardDeck(CardName.province, victoryCardList);
                break;
            case duchy:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.duchy, victoryCardList);
                break;
            case estate:
                addBuy(-1);
                addCoins(-2);
                cardFromHashTableToDiscardDeck(CardName.estate, victoryCardList);
                break;
            case village:
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.village, actionCardList);
                break;
            case woodcutter:
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.woodcutter, actionCardList);
                break;
            case workshop:
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.workshop, actionCardList);
                break;
            case smithy:
                addBuy(-1);
                addCoins(-4);
                cardFromHashTableToDiscardDeck(CardName.smithy, actionCardList);
                break;
            case councilRoom:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.councilRoom, actionCardList);
                break;
            case festival:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.festival, actionCardList);
                break;
            case laboratory:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.laboratory, actionCardList);
                break;
            case witch:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.witch, actionCardList);
                break;
            case chancellor:
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.chancellor, actionCardList);
                break;
            case market:
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.market, actionCardList);
                break;

        }
    }

    public void cardFromHashTableToDiscardDeck(CardName cardName, Hashtable<CardName,Integer> list){
        if(list.get(cardName) != 0){
            int currentCount = list.get(cardName);
            list.put(cardName,currentCount-1);
            currentCount = playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
            playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName,currentCount+1);
        }
    }

    public void playTreasures(){
        tempHandDeck = null;
       tempHandDeck = playerList.get(getCurrentPlayer()).getHandDeck();

       for(Map.Entry<CardName, Integer> entry : tempHandDeck.entrySet()){
           switch(entry.getKey().toString()){

           case "gold": playGold(entry.getValue());
               break;

           case "silver": playSilver(entry.getValue());
               break;

           case "copper": playCopper(entry.getValue());
               break;
       }



       }
    }

    public void playCard(CardName cardName) {

        CardName cardNameToPlay = cardName;

        switch (cardNameToPlay){
            case village:
                playVillage();
                break;
            case woodcutter:
                playWoodcutter();
                break;
            case workshop:
                playWorkshop();
                break;
            case smithy:
                playSmithy();
                break;
            case councilRoom:
                playCouncilRoom();
                break;
            case festival:
                playFestival();
                break;
            case laboratory:
                playLabratory();
                break;
            case witch:
                playWitch();
                break;
            case chancellor:
                playChancellor();
                break;
            case market:
                playMarket();
                break;
        }
    }

    //methods for the action Cards
    public void playVillage() {
        subtractAction(1);
        addAction(2);
        drawCards(1, getCurrentPlayer());
    }


    public void playWoodcutter() {
        subtractAction(1);
        addBuy(1);
        addCoins(2);
    }

    ;

    public void playWorkshop() {
        subtractAction(1);
        addCoins(4);
        addBuy(1);//TODO: how to make them buy but use tempCoins & eifach 4 coins geben fertig
    }

    ;

    public void playSmithy() {
        subtractAction(1);
        drawCards(3, getCurrentPlayer());
    }

    ;

    public void playCouncilRoom() {
        subtractAction(1);
        drawCards(4, getCurrentPlayer());
        addBuy(1);
        int i = 0;
        Random rand = new Random();

        for (Player player : playerList) {
            int playerindex = player.getID();
            if (getCurrentPlayer() != playerindex) {
                drawCards(1, playerindex);
            }
        }

    }

    ;

    public void playFestival() {
        subtractAction(1);
        addAction(2);
        addBuy(1);
        addCoins(2);
    }

    ;

    public void playLabratory() {
        subtractAction(1);
        drawCards(2, getCurrentPlayer());
        addAction(1);
    }

    ;


    public void playWitch() {
        subtractAction(1);
        drawCards(2, getCurrentPlayer());

        int i = 0;
        if (playerList.get(getCurrentPlayer()) != playerList.get(i)) {
            while (i < playerList.size()) {
                playerList.get(i).setHandDeck(CardName.curse, 1);
                int currentCount = victoryCardList.get(CardName.curse);
                victoryCardList.put(CardName.curse, currentCount - 1);
            }
        }
        i++;


    }

    public void playChancellor() {
        subtractAction(1);
        addCoins(2);
        discardDecktoPlayerDeck();

    }

    ;

    public void playMarket() {
        subtractAction(1);
        drawCards(1, getCurrentPlayer());
        addCoins(1);
        addBuy(1);
        addAction(1);
    }

    ;

    //methods for the coin cards
    public void playGold(int amountOfGold) {
        addCoins(3*amountOfGold);
    }

    ;

    public void playSilver(int amountOfSilver) {
        addCoins(2*amountOfSilver);
    }

    ;

    public void playCopper(int amountOfCopper) {
        addCoins(1*amountOfCopper);
    }

    ;


    String getGameName() { return this.gameName; }

    private void subtractAction(int i) {
        playerList.get(getCurrentPlayer()).setActionPoints(-i);
    }

    private int getCurrentPlayer() {
        return turnCount % playerList.size();
    }

    private void addAction(int actionPoints) {
        playerList.get(getCurrentPlayer()).setActionPoints(actionPoints);
    }

    private void addBuy(int buyingPoints) {
        playerList.get(getCurrentPlayer()).setBuyingPoints(buyingPoints);
    }

    private void addCoins(int coins) {
        playerList.get(getCurrentPlayer()).setCoins(coins);
    }

    //private void addTempCoins(int tempCoins) { playerList.get(getCurrentPlayer()).setCoins(tempCoins);}

    //public void setPlayerCount(int playerCount) {
     //   this.playerCount = playerCount;}

    public void setActionCardCount(int actionCardCount) {
        this.actionCardCount = actionCardCount;
    }

    public void setAiPlayer(boolean aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public String getGameResponseMessage() {
        return gameResponseMessage;
    }

    public String gameResponseMessageProperty() {
        return gameResponseMessage;
    }


}