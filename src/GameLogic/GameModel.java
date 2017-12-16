package GameLogic;

import GameLogic.cards.CardName;

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
    private String cardBoughtMessage;
    private String cardBought;


    GameModel(String gameName, String gameResponseMessage) {
        this.gameName = gameName;
        this.gameResponseMessage = gameResponseMessage;
        playerList = new ArrayList<Player>();
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
        startingMessage();
        drawCardMessage(); //TODO ALL BECOME THE SAME CARDS IN ROUND ONE...
    }

    private void initActionCards(int cardsInGame) {
        ArrayList<CardName> actionCardNames = new ArrayList<>();
        CardName[] cardNames = CardName.values();
        int numberOfActionCardInEnumList = cardsInGame;
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
        playerList.get(getCurrentPlayer()).endTurn();
        drawCardMessage();

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



    private void createMessage() {

    }

    private String startingMessage() {

        String startMessage = "start@updateAll/coins,0;buy,1;action,1@actionCards/village,10;woodcutter,10;smithy,10;councilRoom,10;workshop,10@coinCards/gold,30;silver,40;copper,46@victoryCards/province,8;duchy,8;estate,8;curse,10@cardDeckAll/estate,3;copper,7";

        return startMessage;
    }

    private String drawCardMessage(){

        String drawCardMessage = "hand/";

        Hashtable<CardName, Integer> cardsDrawn = drawCards(5,getCurrentPlayer());
        Hashtable<CardName, Integer> cardsRemainingInPlayerDeck = playerList.get(getCurrentPlayer()).getPlayerDeck();
        for (CardName key: cardsDrawn.keySet()){
            drawCardMessage = drawCardMessage+key+","+cardsDrawn.get(key)+";"; //TODO there will be a ; after the last card value and before the @
        }
        drawCardMessage = drawCardMessage+"@deck/";

        for (CardName key: cardsRemainingInPlayerDeck.keySet()){
            drawCardMessage = drawCardMessage+key+","+cardsRemainingInPlayerDeck.get(key)+";";
        }

        return drawCardMessage;
    }

  /*  private String drawCardMessageForAll(){
        ArrayList<String> drawnCardMessageForAllPlayers = null;

        for (int i = 0; i<=playerList.size();i++) {

            String drawCardMessage = ":Player"+i+"hand/";
            Hashtable<CardName, Integer> cardsRemainingInPlayerDeck = playerList.get(i).getPlayerDeck();
            Hashtable<CardName, Integer> cardsDrawn = drawCards(5, getCurrentPlayer());

            for (CardName key : cardsDrawn.keySet()) {
                drawCardMessage = drawCardMessage + key + "," + cardsDrawn.get(key) + ";"; //TODO there will be a ; after the last card value and before the @
            }
            drawCardMessage = drawCardMessage + "@deck/";

            for (CardName key : cardsRemainingInPlayerDeck.keySet()) {
                drawCardMessage = drawCardMessage + key + "," + cardsRemainingInPlayerDeck.get(key) + ";";
            }

            drawnCardMessageForAllPlayers.add(drawCardMessage);
        }

        return drawnCardMessageForAllPlayers.toString();
    }*/


    private String finishTurnMessage(){

        String finishTurnMessage = "TODO"; //TODO finish Round Message

        return finishTurnMessage;
    }

    private String endGameMessage(){

        String endMessage = "TODO"; //TODO END MESSAGE

        return endMessage;
    }

    private String playTreasureMessage(){

        String playTreasureMessage = "discard/";
        Hashtable<CardName, Integer> cardsInPlayerDeck = playerList.get(getCurrentPlayer()).getHandDeck();

        for (CardName cardName: cardsInPlayerDeck.keySet()) {

            switch (cardName.toString()) {
                case "gold":
                    playTreasureMessage = playTreasureMessage + "gold," + cardsInPlayerDeck.get(cardName) + ";";
                    break;
                case "silver":
                    playTreasureMessage = playTreasureMessage + "silver," + cardsInPlayerDeck.get(cardName) + ";";
                    break;
                case "copper":
                    playTreasureMessage = playTreasureMessage + "copper," + cardsInPlayerDeck.get(cardName) + ";";
                    break;
            }
        }

            playTreasureMessage = playTreasureMessage + "@hand/";

            for (CardName cardName1: cardsInPlayerDeck.keySet()){

                switch (cardName1.toString()){
                    case "gold":
                        playTreasureMessage = playTreasureMessage + "gold,0;";
                        break;
                    case "silver":
                        playTreasureMessage = playTreasureMessage + "silver,0;";
                        break;
                    case "copper":
                        playTreasureMessage = playTreasureMessage + "copper,0;";
                        break;
                }

            //playTreasureMessage = playTreasureMessage + "@update/coins,"+playerList.get(getCurrentPlayer()).getCoins();
        }

        return playTreasureMessage;

    }

    public String buyCardMessage(){

        /*String buyCardMessage = "update/buy," + playerList.get(getCurrentPlayer()).getBuy()+ ";coins," + playerList.get(getCurrentPlayer()).getCoins() + ";@" + cardBoughtMessage;

        for(CardName cardName: actionCardList.keySet()){
            if(cardName.toString()==cardBought){
                buyCardMessage = buyCardMessage + actionCardList.get(cardName);
            }
        }
        buyCardMessage = buyCardMessage + ";@discard/" + cardBought + ",";

        for(CardName cardName: playerList.get(getCurrentPlayer()).getDiscardDeck().keySet()){
            if(cardName.toString()==cardBought){
                buyCardMessage = buyCardMessage + playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
            }
        }

        return buyCardMessage;*/
        return null;
    }

    public String playCardMessage(){

        String playCardmessage = "TODO"; //TODO PLAY CARD MESSAGE

        return playCardmessage;
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
    private Hashtable<CardName, Integer> drawCards(int amountToDraw, int playerIndex) {
        ArrayList<CardName> cardNames = new ArrayList<>();
        Hashtable<CardName, Integer> listOfCardsDrawnForMessage = null;

        for (CardName cardName : playerList.get(playerIndex).getPlayerDeck().keySet()) {
            cardNames.add(cardName);

        }
        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();

        while (cardsDrawn < amountToDraw) {
            cardName = cardNames.get(rand.nextInt(cardNames.size()));
            listOfCardsDrawnForMessage.put(cardName, 1);

            if (playerList.get(getCurrentPlayer()).getPlayerDeck().get(cardName) != 0) {
                int currentCount = playerList.get(playerIndex).getPlayerDeck().get(cardName);
                playerList.get(playerIndex).getPlayerDeck().put(cardName, currentCount - 1);
                checkifPlayerDeckisEmpty();
                currentCount = playerList.get(playerIndex).getHandDeck().get(cardName);
                playerList.get(playerIndex).getHandDeck().put(cardName, currentCount + 1);
                cardsDrawn++;
            }
        }

        return listOfCardsDrawnForMessage;
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
    private void discardDecktoPlayerDeck() {
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

    public void buyCard(String cardName) {

        switch (cardName){
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
            case "councilRoom":
                addBuy(-1);
                addCoins(-5);
                cardFromHashTableToDiscardDeck(CardName.councilRoom, actionCardList);
                cardBoughtMessage = "actionCards/councilRoom,";
                cardBought = "councilRoom";
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
        buyCardMessage();
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

       playTreasureMessage();
    }

    public void playCard(String cardName) {

        ;

        switch (cardName){
            case "village":
                playVillage();
                break;
            case "woodcutter":
                playWoodcutter();
                break;
            case "workshop":
                playWorkshop();
                break;
            case "smithy":
                playSmithy();
                break;
            case "councilRoom":
                playCouncilRoom();
                break;
            case "festival":
                playFestival();
                break;
            case "laboratory":
                playLabratory();
                break;
            case "witch":
                playWitch();
                break;
            case "chancellor":
                playChancellor();
                break;
            case "market":
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