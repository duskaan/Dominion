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
    private SimpleStringProperty gameResponseMessage;
    private String cardBoughtMessage;
    private String cardBought;
    private String actionCardPlayed;
    private Hashtable<CardName, Integer> listOfCardsDrawnForMessage;



    GameModel(String gameName, SimpleStringProperty gameResponseMessage) {
        this.gameName = gameName;
        this.gameResponseMessage = gameResponseMessage;
        playerList = new ArrayList<Player>();
        actionCardList = new Hashtable<>();
        coinCardList = new Hashtable<>();
        victoryCardList = new Hashtable<>();
        listOfCardsDrawnForMessage = new Hashtable<>();

    }

    public void addPlayer(String playerName, int playerCount) {
        if (playerList == null) {
            playerList = new ArrayList<>();
        }
        int playerID = playerList.size();

        if (playerID <= playerCount - 1) {
            Player player = new Player(playerID, playerName);
            playerList.add(player);
        }
    }


  /*  void removePlayer(int playerID) {
        if (playerList.size() >= playerID) {
            playerList.remove(playerID);
            for (int i = 0; i <= playerList.size() - 1; i++) {
                playerList.get(i).setID(i);
            }
        }
    }
    */


    void init(int cardsInGame) { //TODO HOW GOES THE MESSAGE FROM HERE
        initActionCards(cardsInGame);
        initCoinCardCount();
        initVictoryCurseCards();
        initPlayers();
        //startingMessage();
        //drawCardMessage(); //TODO ALL BECOME THE SAME CARDS IN ROUND ONE...
    }

    private void initActionCards(int cardsInGame) {
        ArrayList<CardName> actionCardNames = new ArrayList<>();
        CardName[] cardNames = CardName.values();
        for (int i = 0; i < cardsInGame; i++) {
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
        //endTurnMessage();
        discardHandDeckToDiscardDeck();
        //isGameOver();
        playerList.get(getCurrentPlayer()).endTurn();
        //drawCardMessage(5); //does draw cards as well !!!
    }

    public void turnCount() {
        turnCount++;
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

    public String startingMessage(int cardsInGame) { //TODO no start message
        String startMessage;
        if(cardsInGame==5){
            startMessage = "start@5";
        } else {
            startMessage = "start@10";
        }
        return startMessage;
    }
    //drawCardMessageWithIndex --> newCards@PlayerName1@hand/estate,2;copper,3@deck,5   ALRIGHT!

    public String drawCardMessageWithIndex(int playerindex) {
        String drawCardMessage = "newCards@" + playerList.get(playerindex).getName()+ "@hand/";

        Hashtable<CardName, Integer> cardsRemainingInPlayerDeck = playerList.get(playerindex).getPlayerDeck();
        Hashtable<CardName, Integer> playerHandDeck = playerList.get(playerindex).getHandDeck();
        Set<CardName> keys = playerHandDeck.keySet();
        Iterator<CardName> itr = keys.iterator();

        while (itr.hasNext()) {
            CardName cardName = itr.next();
            if(playerHandDeck.get(cardName)!=0) {
                drawCardMessage = drawCardMessage + cardName + "," + playerHandDeck.get(cardName);
            }
            if (itr.hasNext()){
                drawCardMessage = drawCardMessage + ";";
            }
        }
        drawCardMessage = drawCardMessage + "@deck,";

        int amountOfDeckCards = 0;
        for (CardName key : cardsRemainingInPlayerDeck.keySet()) {
            amountOfDeckCards = amountOfDeckCards + cardsRemainingInPlayerDeck.get(key);
        }
        drawCardMessage = drawCardMessage + amountOfDeckCards;

        return drawCardMessage;
    }

    //endTurn@PlayerName1@vitoryPoints,5@discard,10  ALRIGHT;

    public String endTurnMessage() {
        calculateVictoryPoints();
        String endTurnMessage = "endTurn@" + playerList.get(getCurrentPlayer()).getName() + "@victoryPoints," + playerList.get(getCurrentPlayer()).getVictoryPoints();

        Hashtable<CardName, Integer> cardsOnHand = playerList.get(getCurrentPlayer()).getHandDeck();
        Hashtable<CardName,Integer> cardsInDiscardDeck = playerList.get(getCurrentPlayer()).getDiscardDeck();

        endTurnMessage = endTurnMessage + "@discard,";

        int discardAmount = 0;

        for(CardName cardName: cardsOnHand.keySet()){
            if(cardsOnHand.get(cardName)!=0){
                discardAmount = discardAmount + cardsOnHand.get(cardName);
            }
        }
        for (CardName cardName: cardsInDiscardDeck.keySet()){
            if(cardsInDiscardDeck.get(cardName)!=0){
                discardAmount = discardAmount + cardsInDiscardDeck.get(cardName);
            }
        }
        endTurnMessage = endTurnMessage + discardAmount;

        return endTurnMessage;

    }

    //newDeckValue@PlayerName1@discard,10@deck,10
    public String discardMessage() {

        String discardMessage = "newDeckValues@" + playerList.get(getCurrentPlayer()).getName() + "@discard,";

        Hashtable<CardName, Integer> playerDeck = playerList.get(getCurrentPlayer()).getPlayerDeck();
        Hashtable<CardName, Integer> discardDeck = playerList.get(getCurrentPlayer()).getDiscardDeck();
        int amountInDiscardDeck = 0;
        int amountInPlayerDeck = 0;

        for(CardName cardName: discardDeck.keySet()){
            amountInDiscardDeck = amountInDiscardDeck + discardDeck.get(cardName);
        }

        discardMessage = discardMessage + amountInDiscardDeck + "@deck,";

        for (CardName cardName: playerDeck.keySet()){
            amountInPlayerDeck = amountInPlayerDeck + playerDeck.get(cardName);
        }
        discardMessage = discardMessage + amountInPlayerDeck;

        return discardMessage;
    }

    //end@PlayerName1,17@PlayerName2,18@Playername3,17
    public String endGameMessage() {

        String endMessage = "end@";
        calculateVictoryPoints();
        Iterator<Player> itr1 = playerList.iterator();

        while (itr1.hasNext()) {
            Player playerName = itr1.next();
            endMessage = endMessage + playerName.getName() + "," + playerName.getVictoryPoints();
            if (itr1.hasNext()){
                endMessage = endMessage + "@";
            }
        }
        return endMessage;
    }

    //playTreasures@PlayerName1@playedCard/copper,3;gold,3@hand/copper,2,silver,3@coinValue,1
    public String playTreasureMessage() {

        String playTreasureMessage = "playTreasures@" + playerList.get(getCurrentPlayer()).getName() + "@playedCard/";
        String coinCard = null;
        int gold = 0;
        int silver = 0;
        int copper = 0;
        Hashtable<CardName, Integer> cardsInPlayerDeck = playerList.get(getCurrentPlayer()).getHandDeck();
        Set<CardName> key = cardsInPlayerDeck.keySet();
        Iterator<CardName> itr = key.iterator();

        while (itr.hasNext()){
            coinCard = itr.next().toString();

            switch (coinCard) {
                case "gold":
                    gold = gold + 1;
                    break;
                case "silver":
                    silver = silver + 1;
                    break;
                case "copper":
                    copper = copper + 1;
                    break;
                default:
                    break;
            }
            if (!itr.hasNext()){
                if (gold>0){
                    playTreasureMessage = playTreasureMessage + "gold," + gold;
                    if(silver>0||copper>0){
                        playTreasureMessage = playTreasureMessage + ";";
                    }
                }
                if (silver>0){
                    playTreasureMessage = playTreasureMessage + "silver," + silver;
                    if(copper>0){
                        playTreasureMessage = playTreasureMessage + ";";
                    }
                }
                if(copper>0){
                    playTreasureMessage = playTreasureMessage + "copper," + copper;
                }
            }
        }

        playTreasureMessage = playTreasureMessage + "@hand/";
        Set<CardName> key1 = cardsInPlayerDeck.keySet();
        Iterator<CardName> itr1 = key1.iterator();

        while (itr1.hasNext()) {
            CardName cardName = itr1.next();
            if (!cardName.toString().equals("gold") | !cardName.toString().equals("silver") | !cardName.toString().equals("copper")){
                playTreasureMessage = playTreasureMessage + cardName.toString() + "," + cardsInPlayerDeck.get(cardName).toString();
            }

            if (itr1.hasNext()){
                playTreasureMessage = playTreasureMessage + ";";
            }

            if(!itr1.hasNext()){
                if(playTreasureMessage.charAt(playTreasureMessage.length()-1)==';')   {
                     playTreasureMessage = playTreasureMessage.substring(0, playTreasureMessage.length() - 1);
                 }
            }

        }
            playTreasureMessage = playTreasureMessage + "@coinValue," + playerList.get(getCurrentPlayer()).getCoins();

        return playTreasureMessage;
    }

    //buy@PlayerName1@buyValue,0@coinValue,0@actionCards/woodcutter,9@discard,10       ALRIGHT!!!
    //                                       new value of actioncards/ new value of discard deck
    public String buyCardMessage() {

        String buyCardMessage = "buy@" + playerList.get(getCurrentPlayer()).getName() + "@buyValue," + playerList.get(getCurrentPlayer()).getBuy() + "@coinValue," + playerList.get(getCurrentPlayer()).getCoins() + "@" + cardBoughtMessage;

        buyCardMessage = addToBuyMessageTheAmountOfCardBought(actionCardList,buyCardMessage);
        buyCardMessage = addToBuyMessageTheAmountOfCardBought(victoryCardList,buyCardMessage);
        buyCardMessage = addToBuyMessageTheAmountOfCardBought(coinCardList,buyCardMessage);

        buyCardMessage = buyCardMessage + "@discard,";

        int discardAmount = 0;
        for (CardName cardName: playerList.get(getCurrentPlayer()).getDiscardDeck().keySet()){
            discardAmount = discardAmount + playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
        }
        buyCardMessage = buyCardMessage + discardAmount;

        return buyCardMessage;
    }

    public String addToBuyMessageTheAmountOfCardBought(Hashtable<CardName,Integer> list, String buyCardMessage){
        for (CardName cardName : list.keySet()) {
            if (cardName.toString().equals(cardBought)) {
                buyCardMessage = buyCardMessage + list.get(cardName);
            }
        }
        return buyCardMessage;
    }
    //play@PlayerName1@village@actionValue,0
    public String playCardMessage() {

        String playCardMessage = "play@" + playerList.get(getCurrentPlayer()).getName() + "@" + actionCardPlayed + "@actionValue," + playerList.get(getCurrentPlayer()).getActions();
        return playCardMessage;
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
        if (numberOfProvinceCard == 0) {
            calculateVictoryPoints();
            return true;
        }
        return false;
    }


    public void calculateVictoryPoints() {

        int i = 0;
        int playerSize = playerList.size();

        while (i < playerSize) {
            int provinceAmount = playerList.get(i).getPlayerDeck().get(CardName.province);
            int estateAmount = playerList.get(i).getPlayerDeck().get(CardName.estate);
            int duchyAmount = playerList.get(i).getPlayerDeck().get(CardName.duchy);
            int curseAmount = playerList.get(i).getPlayerDeck().get(CardName.curse);

            int VictoryPoints = provinceAmount * 6 + duchyAmount * 3 + estateAmount + curseAmount * -1;

            playerList.get(i).setVictoryPoints(VictoryPoints);
            i++;
        }
    }


    //draw card from playerDeck
    public void drawCards(int amountToDraw, int playerIndex) {
        ArrayList<CardName> cardNames = new ArrayList<>();


        for (CardName cardName : playerList.get(playerIndex).getPlayerDeck().keySet()) {
            cardNames.add(cardName);

        }
        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();

        while (cardsDrawn < amountToDraw) {
            cardName = cardNames.get(rand.nextInt(cardNames.size()));

            if (playerList.get(playerIndex).getPlayerDeck().get(cardName) != 0) {
                if (listOfCardsDrawnForMessage.contains(cardName)) {
                    listOfCardsDrawnForMessage.put(cardName, listOfCardsDrawnForMessage.get(cardName) + 1);
                } else listOfCardsDrawnForMessage.put(cardName, 1);

                int currentCount = playerList.get(playerIndex).getPlayerDeck().get(cardName);
                playerList.get(playerIndex).getPlayerDeck().put(cardName, currentCount - 1);
                currentCount = playerList.get(playerIndex).getHandDeck().get(cardName);
                playerList.get(playerIndex).getHandDeck().put(cardName, currentCount + 1);
                cardsDrawn++;
            }
        }
    }

    public void drawCurse(int amountToDraw, int playerIndex) {
        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();
        cardName = CardName.curse;

        if (victoryCardList.get(cardName)!= 0) {
            while (cardsDrawn < amountToDraw) {
                if (listOfCardsDrawnForMessage.contains(cardName)) {
                    listOfCardsDrawnForMessage.put(cardName, listOfCardsDrawnForMessage.get(cardName) + 1);
                } else listOfCardsDrawnForMessage.put(cardName, 1);
                int currentCount = victoryCardList.get(cardName);
                victoryCardList.put(cardName, currentCount - 1);
                currentCount = playerList.get(playerIndex).getDiscardDeck().get(cardName);
                playerList.get(playerIndex).getDiscardDeck().put(cardName, currentCount + 1);
                cardsDrawn++;
            }
        }
    }


    public boolean checkifPlayerDeckisEmpty() {

        boolean hasCards = false;
        for (CardName cardName : playerList.get(getCurrentPlayer()).getPlayerDeck().keySet()) {
            if (playerList.get(getCurrentPlayer()).getPlayerDeck().get(cardName) > 0) {
                hasCards = true;
            }
        }
        return hasCards;
    }

    //When the player deck is empty discardDeck will be shuffled nd put into playerDeck
    public String discardDecktoPlayerDeck(int playerIndex) {
        ArrayList<CardName> cardNames = new ArrayList<>();
        for (CardName cardName : playerList.get(playerIndex).getPlayerDeck().keySet()) {
            cardNames.add(cardName);
        }
        CardName cardName = null;

        for (int i = 0; i < playerList.get(playerIndex).getDiscardDeck().size(); i++) {
            cardName = cardNames.get(i);

            if (playerList.get(playerIndex).getDiscardDeck().get(cardName) != 0) {
                int currentCount = playerList.get(playerIndex).getDiscardDeck().get(cardName);
                playerList.get(playerIndex).getDiscardDeck().put(cardName, 0);
                playerList.get(playerIndex).getPlayerDeck().put(cardName, currentCount);
            }
        }

        return discardMessage();

    }

    public String buyCard(String cardName) {

        switch (cardName) {
            case "gold":
                addBuy(-1);
                addCoins(-6);
                cardFromHashTableToDiscardDeck(CardName.gold, coinCardList);
                cardBoughtMessage = "coinCards/gold,";
                cardBought = "gold";
                break;
            case "silver":
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.silver, coinCardList);
                cardBoughtMessage = "coinCards/silver,";
                cardBought = "silver";
                break;
            case "copper":
                addBuy(-1);
                cardFromHashTableToDiscardDeck(CardName.copper, coinCardList);
                cardBoughtMessage = "coinCards/copper,";
                cardBought = "copper";
                break;
            case "province":
                addBuy(-1);
                addCoins(-8);
                cardFromHashTableToDiscardDeck(CardName.province, victoryCardList);
                cardBoughtMessage = "victoryCards/province,";
                cardBought = "province";
                break;
            case "duchy":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.duchy, victoryCardList);
                cardBoughtMessage = "victoryCards/duchy,";
                cardBought = "duchy";
                break;
            case "estate":
                addBuy(-1);
                addCoins(-2);
                cardFromHashTableToDiscardDeck(CardName.estate, victoryCardList);
                cardBoughtMessage = "victoryCards/estate,";
                cardBought = "estate";
                break;
            case "village":
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.village, actionCardList);
                cardBoughtMessage = "actionCards/village,";
                cardBought = "village";
                break;
            case "woodcutter":
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.woodcutter, actionCardList);
                cardBoughtMessage = "actionCards/woodcutter,";
                cardBought = "woodcutter";
                break;
            case "workshop":
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.workshop, actionCardList);
                cardBoughtMessage = "actionCards/workshop,";
                cardBought = "workshop";
                break;
            case "smithy":
                addBuy(-1);
                addCoins(-4);
                cardFromHashTableToDiscardDeck(CardName.smithy, actionCardList);
                cardBoughtMessage = "actionCards/smithy,";
                cardBought = "smithy";
                break;
            case "councilroom":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.councilroom, actionCardList);
                cardBoughtMessage = "actionCards/councilroom,";
                cardBought = "councilroom";
                break;
            case "festival":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.festival, actionCardList);
                cardBoughtMessage = "actionCards/festival,";
                cardBought = "festival";
                break;
            case "laboratory":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.laboratory, actionCardList);
                cardBoughtMessage = "actionCards/laboratory,";
                cardBought = "laboratory";
                break;
            case "witch":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.witch, actionCardList);
                cardBoughtMessage = "actionCards/witch,";
                cardBought = "witch";
                break;
            case "chancellor":
                addBuy(-1);
                addCoins(-3);
                cardFromHashTableToDiscardDeck(CardName.chancellor, actionCardList);
                cardBoughtMessage = "actionCards/chancellor,";
                cardBought = "chancellor";
                break;
            case "market":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.market, actionCardList);
                cardBoughtMessage = "actionCards/market,";
                cardBought = "market";
                break;

        }
        return buyCardMessage();
    }

    public void cardFromHashTableToDiscardDeck(CardName cardName, Hashtable<CardName, Integer> list) {
        if (list.get(cardName) != 0) {
            int currentCount = list.get(cardName);
            list.put(cardName, currentCount - 1);
            currentCount = playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
            playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName, currentCount + 1);
        }
    }

    public void playTreasures() {

        tempHandDeck = playerList.get(getCurrentPlayer()).getHandDeck();

        for (Map.Entry<CardName, Integer> entry : tempHandDeck.entrySet()) {
            switch (entry.getKey().toString()) {

                case "gold":
                    playGold(entry.getValue());
                    break;

                case "silver":
                    playSilver(entry.getValue());
                    break;

                case "copper":
                    playCopper(entry.getValue());
                    break;


            }
        }
    }

    public String playCard(String cardName) {

        switch (cardName) {
            case "village":
                playVillage();
                actionCardPlayed = "village";
                break;
            case "woodcutter":
                playWoodcutter();
                actionCardPlayed = "woodcutter";
                break;
            case "workshop":
                playWorkshop();
                actionCardPlayed = "workshop";
                break;
            case "smithy":
                playSmithy();
                actionCardPlayed = "smithy";
                break;
            case "councilroom":
                playCouncilRoom();
                actionCardPlayed = "councilroom";
                break;
            case "festival":
                playFestival();
                actionCardPlayed = "festival";
                break;
            case "laboratory":
                playLaboratory();
                actionCardPlayed = "laboratory";
                break;
            case "witch":
                playWitch();
                actionCardPlayed = "witch";
                break;
            case "chancellor":
                playChancellor();
                actionCardPlayed = "chancellor";
                break;
            case "market":
                playMarket();
                actionCardPlayed = "market";
                break;
        }
        return playCardMessage();
    }

    //methods for the action Cards
    public void playVillage() {
        subtractAction(1);
        addAction(2);
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
        addBuy(1);
    }

    ;

    public void playSmithy() {
        subtractAction(1);
    }

    ;

    public void playCouncilRoom() {
        subtractAction(1);
        addBuy(1);
        int i = 0;
        Random rand = new Random();
    }

    ;

    public void playFestival() {
        subtractAction(1);
        addAction(2);
        addBuy(1);
        addCoins(2);
    }

    ;

    public void playLaboratory() {
        subtractAction(1);
        addAction(1);
    }

    ;


    public void playWitch() {
        subtractAction(1);

    }

    public void playChancellor() {
        subtractAction(1);
        addCoins(2);

    }

    ;

    public void playMarket() {
        subtractAction(1);
        addCoins(1);
        addBuy(1);
        addAction(1);
    }

    ;

    //methods for the coin cards
    public void playGold(int amountOfGold) {
        addCoins(3 * amountOfGold);
    }

    ;

    public void playSilver(int amountOfSilver) {
        addCoins(2 * amountOfSilver);
    }

    ;

    public void playCopper(int amountOfCopper) {
        addCoins(1 * amountOfCopper);
    }

    ;


    String getGameName() {
        return this.gameName;
    }

    private void subtractAction(int i) {
        playerList.get(getCurrentPlayer()).setActionPoints(-i);
    }

    public int getCurrentPlayer() {
        return turnCount % (playerList.size() - 1); //TODO IS THIS ALRIGHT
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

    public SimpleStringProperty getGameResponseMessage() {
        return gameResponseMessage;
    }

    public SimpleStringProperty gameResponseMessageProperty() {
        return gameResponseMessage;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}