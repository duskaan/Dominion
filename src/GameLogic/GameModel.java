package GameLogic;

import GameLogic.cards.CardName;
import Server.LogHandling;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;
import java.util.logging.Level;

public class GameModel {

    //information needed for methods in GameModel
    private ArrayList<Player> playerList;
    private int turnCount = 0;
    private Hashtable<CardName, Integer> actionCardList;
    private Hashtable<CardName, Integer> coinCardList;
    private Hashtable<CardName, Integer> victoryCardList;
    private String gameName;
    private String cardBoughtMessage;
    private String cardBought;
    private String actionCardPlayed;
    private boolean canIPlay = true;
    private Hashtable<CardName, Integer> listOfCardsDrawnForMessage;


    GameModel(String gameName, SimpleStringProperty gameResponseMessage) {
        this.gameName = gameName;
        playerList = new ArrayList<Player>();
        actionCardList = new Hashtable<>();
        coinCardList = new Hashtable<>();
        victoryCardList = new Hashtable<>();
        listOfCardsDrawnForMessage = new Hashtable<>();

    }

    //@Damiano Nardone
    //this methods creates ArrayLists with Players in them according to the playerCount
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

    //@Damiano Nardone
    //this methods initializes the actionCards, coinCards & victoryCards on the field. And give all the Player cards
    void init(int cardsInGame) {
        initLists();
        initActionCards(cardsInGame);
        initCoinCardCount();
        initVictoryCards();
        initPlayers();
    }

    //@Damiano
    //this method fill all Hashtable with possible entries to avoid nullPointerExecptions
    private void initLists() {
        coinCardList.put(CardName.gold, 0);
        coinCardList.put(CardName.silver, 0);
        coinCardList.put(CardName.copper, 0);
        actionCardList.put(CardName.village,0);
        actionCardList.put(CardName.woodcutter,0);
        actionCardList.put(CardName.workshop,0);
        actionCardList.put(CardName.smithy,0);
        actionCardList.put(CardName.councilroom,0);
        actionCardList.put(CardName.festival, 0);
        actionCardList.put(CardName.witch,0);
        actionCardList.put(CardName.chancellor,0);
        actionCardList.put(CardName.market,0);
        actionCardList.put(CardName.laboratory,0);
        victoryCardList.put(CardName.province,0);
        victoryCardList.put(CardName.duchy,0);
        victoryCardList.put(CardName.estate,0);
        victoryCardList.put(CardName.curse,0);
    }



    //@Damiano Nardone
    //this methods fill up the Hashtable actionCardList with actionCards that will be on the field
    private void initActionCards(int cardsInGame) {
        ArrayList<CardName> actionCardNames = new ArrayList<>();
        CardName[] cardNames = CardName.values();
        for (int i = 0; i < cardsInGame; i++) {
            actionCardNames.add(cardNames[i]);
        }

        for (int i = 0; i < cardsInGame; i++) {
            actionCardList.put(actionCardNames.get(i), 10);
        }
    }

    //@Damiano Nardone
    //this method fill the Hashtable victoryCardList with the estate,duchy, province and curse with the right amount
    private void initVictoryCards() {
        victoryCardList.put(CardName.estate, 8);
        victoryCardList.put(CardName.duchy, 8);
        victoryCardList.put(CardName.province, 8);
        victoryCardList.put(CardName.curse, 10);
    }

    //@Damiano Nardone
    //this method fill the hashtable coinCardList with gold,silver and copper in the right amount
    private void initCoinCardCount() {
        coinCardList.put(CardName.gold, 30);
        coinCardList.put(CardName.silver, 40);
        coinCardList.put(CardName.copper, 46);
    }

    //@Damiano Nardone
    //this methods fills up the Hashtable Player Deck in the Player class with 3 estate and 7 copper
    private void initPlayers() {
        for (Player player : playerList) {
            player.setCard(CardName.estate, 3);
            player.setCard(CardName.copper, 7);
        }
    }

    //@Damiano Nardone
    //this method calls discardHandDeckTODiscardDeck() method and the endTurn() method in the Player class also it puts the boolean canIPlay back to true
    public void endTurn() {
        discardHandDeckToDiscardDeck();
        playerList.get(getCurrentPlayer()).endTurn();
        canIPlay = true;
    }

    //@Damiano Nardone
    //this method increases the trunCount by one each time called upon
    public void turnCount() {
        turnCount= turnCount+1;
        LogHandling.logOnFile(Level.INFO, "turncount is increased to "+turnCount);
    }


    //@Damiano Nardone
    //this methods is called when a turn ends and puts all the cards in the HandDeck Hashtable into the DiscardDeck Hashtable in the Player class
    private void discardHandDeckToDiscardDeck() {
        for (CardName cardName : playerList.get(getCurrentPlayer()).getHandDeck().keySet()) {
            if (playerList.get(getCurrentPlayer()).getHandDeck().get(cardName) != 0) {
                int currentCount = playerList.get(getCurrentPlayer()).getHandDeck().get(cardName);
                playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName, currentCount);
                playerList.get(getCurrentPlayer()).getHandDeck().put(cardName, 0);
            }
        }
    }

    //initCards@PlayerName@
    public String initCardsMessage(int playerindex) {
        String initCardsMessage = "initCards@" + playerList.get(playerindex).getName() + "@";
        Set<CardName> keys = listOfCardsDrawnForMessage.keySet();
        Iterator<CardName> itr = keys.iterator();
        while (itr.hasNext()) {
            CardName cardName = itr.next();
            int amount = listOfCardsDrawnForMessage.get(cardName);
            for (int i = 0; i < amount; i++) {
                initCardsMessage = initCardsMessage + cardName;
                if (i != amount) {
                    initCardsMessage = initCardsMessage + "@";
                }
            }

        }

        return initCardsMessage;
    }

    //@Damiano Nardone
    //this method creates the following Message for the Server to pass to the client when the client draws new cards
    //drawCardMessageWithIndex --> newCards@PlayerName1@hand/estate,2;copper,3@deck,5
    public String drawCardMessageWithIndex(int playerIndex) {
        String drawCardMessage = "newCards@" + playerList.get(playerIndex).getName() + "@hand/";

        Hashtable<CardName, Integer> playerHandDeck = playerList.get(playerIndex).getHandDeck();
        Set<CardName> keys = playerHandDeck.keySet();
        Iterator<CardName> itr = keys.iterator();

        for(CardName cardName : listOfCardsDrawnForMessage.keySet()){
            drawCardMessage = drawCardMessage + cardName + "," + listOfCardsDrawnForMessage.get(cardName) + ";";
        }
        drawCardMessage = deleteLastSign(drawCardMessage,';') + "@deck,";

        int amountOfDeckCards = playerList.get(playerIndex).getDeckAmount();
        drawCardMessage = drawCardMessage + amountOfDeckCards;

        return drawCardMessage;
    }

    public String deleteLastSign(String message,char delimiter){
        if (message.charAt(message.length()-1)==delimiter) {
            message = message.substring(0, message.length() - 1);
        }
        return message;
    }

    //@Damiano Nardone
    //this method creates the following message for the server to pass to the client when he ends the turn
    //so victoryPoints are updated and discardDeck
    //endTurn@PlayerName1@vitoryPoints,5@discard,10
    public String endTurnMessage() {
        calculateVictoryPoints();
        String endTurnMessage = "endTurn@" + playerList.get(getCurrentPlayer()).getName() + "@victoryPoints," + playerList.get(getCurrentPlayer()).getVictoryPoints();

        Hashtable<CardName, Integer> cardsOnHand = playerList.get(getCurrentPlayer()).getHandDeck();
        Hashtable<CardName, Integer> cardsInDiscardDeck = playerList.get(getCurrentPlayer()).getDiscardDeck();

        endTurnMessage = endTurnMessage + "@discard,";

        int discardAmount = 0;

        for (CardName cardName : cardsOnHand.keySet()) {
            if (cardsOnHand.get(cardName) != 0) {
                discardAmount = discardAmount + cardsOnHand.get(cardName);
            }
        }
        for (CardName cardName : cardsInDiscardDeck.keySet()) {
            if (cardsInDiscardDeck.get(cardName) != 0) {
                discardAmount = discardAmount + cardsInDiscardDeck.get(cardName);
            }
        }
        endTurnMessage = endTurnMessage + discardAmount;

        return endTurnMessage;

    }

    //@Damiano Nardone
    //this method creates the follwing message for the server to pass to the client when the client is drawing cards
    // and has not enough left in his deck, so the discardDeck goes into the PlayerDeck
    //newDeckValue@PlayerName1@discard,10@deck,10
    public String discardMessage(int playerindex) {

        String discardMessage = "newDeckValues@" + playerList.get(playerindex).getName() + "@discard,";
        discardMessage = discardMessage + playerList.get(playerindex).getDiscardAmount() + "@deck," + playerList.get(playerindex).getDeckAmount();

        return discardMessage;
    }

    //@Damiano Nardone
    //this method creates the following message for the server to pass to the client, when the game ends
    //to tell the clients their final victoryPoints
    //end@PlayerName1,17@PlayerName2,18@Playername3,17
    public String endGameMessage() {

        String endMessage = "end@";
        calculateVictoryPoints();
        Iterator<Player> itr1 = playerList.iterator();

        while (itr1.hasNext()) {
            Player playerName = itr1.next();
            endMessage = endMessage + playerName.getName() + "," + playerName.getVictoryPoints();
            if (itr1.hasNext()) {
                endMessage = endMessage + "@";
            }
        }
        return endMessage;
    }

    //@Damiano Nardone
    //this method creates the follwing message for the server to pass to the client, when the client
    //playsTreasures to update his hand and coins
    //playTreasures@PlayerName1@playedCard/copper,3;gold,3@hand/copper,2,silver,3@coinValue,1
    public String playTreasureMessage() {

        String playTreasureMessage = "playTreasures@" + playerList.get(getCurrentPlayer()).getName() + "@playedCard/";
        int gold = 0;
        int silver = 0;
        int copper = 0;
        Hashtable<CardName, Integer> cardsInPlayerDeck = playerList.get(getCurrentPlayer()).getHandDeck();
        Set<CardName> key = cardsInPlayerDeck.keySet();
        Iterator<CardName> itr = key.iterator();

        while (itr.hasNext()) {
            CardName coinCard = itr.next();
            int amount = cardsInPlayerDeck.get(coinCard);

            switch (coinCard.toString()) {
                case "gold":
                    gold = gold + amount;
                    break;
                case "silver":
                    silver = silver + amount;
                    break;
                case "copper":
                    copper = copper + amount;
                    break;
                default:
                    break;
            }
            if (!itr.hasNext()) {
                if (gold > 0) {
                    playTreasureMessage = playTreasureMessage + "gold," + gold;
                    if (silver > 0 || copper > 0) {
                        playTreasureMessage = playTreasureMessage + ";";
                    }
                }
                if (silver > 0) {
                    playTreasureMessage = playTreasureMessage + "silver," + silver;
                    if (copper > 0) {
                        playTreasureMessage = playTreasureMessage + ";";
                    }
                }
                if (copper > 0) {
                    playTreasureMessage = playTreasureMessage + "copper," + copper;
                }
            }
        }

        playTreasureMessage = playTreasureMessage + "@hand/";
        Set<CardName> key1 = cardsInPlayerDeck.keySet();
        Iterator<CardName> itr1 = key1.iterator();

        while (itr1.hasNext()) {
            CardName cardName = itr1.next();
            if (!cardName.toString().equals("gold") && !cardName.toString().equals("silver") && !cardName.toString().equals("copper")&&cardsInPlayerDeck.get(cardName)!=0) {
                playTreasureMessage = playTreasureMessage + cardName.toString() + "," + cardsInPlayerDeck.get(cardName).toString() + ";";
            }

            if (!itr1.hasNext()) {
                if (playTreasureMessage.charAt(playTreasureMessage.length() - 1) == ';') {
                    playTreasureMessage = playTreasureMessage.substring(0, playTreasureMessage.length() - 1);
                }
            }
        }
        if (playTreasureMessage.charAt(playTreasureMessage.length()-1) == '/') {
            playTreasureMessage = playTreasureMessage + "empty";
        }
        playTreasureMessage = playTreasureMessage + "@coinValue," + playerList.get(getCurrentPlayer()).getCoins();

        return playTreasureMessage;
    }

    //@Damiano Nardone
    //this method creates the following the message for the server to pass on to the client
    //when the client buys something to update coins and actionCard on field and the amount of discard
    //buy@PlayerName1@buyValue,0@coinValue,0@actionCards/woodcutter,9@discard,10       ALRIGHT!!!
    public String buyCardMessage() {

        String buyCardMessage = "buy@" + playerList.get(getCurrentPlayer()).getName() + "@buyValue," + playerList.get(getCurrentPlayer()).getBuy() + "@coinValue," + playerList.get(getCurrentPlayer()).getCoins() + "@" + cardBoughtMessage;

        buyCardMessage = addToBuyMessageTheAmountOfCardBought(actionCardList, buyCardMessage);
        buyCardMessage = addToBuyMessageTheAmountOfCardBought(victoryCardList, buyCardMessage);
        buyCardMessage = addToBuyMessageTheAmountOfCardBought(coinCardList, buyCardMessage);

        buyCardMessage = buyCardMessage + "@discard,";

        int discardAmount = 0;
        for (CardName cardName : playerList.get(getCurrentPlayer()).getDiscardDeck().keySet()) {
            discardAmount = discardAmount + playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
        }
        buyCardMessage = buyCardMessage + discardAmount;

        return buyCardMessage;
    }

    //@Damiano Nardone
    //this method adds the amount of card bought to the message of buyCardMessage
    public String addToBuyMessageTheAmountOfCardBought(Hashtable<CardName, Integer> list, String buyCardMessage) {
        for (CardName cardName : list.keySet()) {
            if (cardName.toString().equals(cardBought)) {
                buyCardMessage = buyCardMessage + list.get(cardName);
            }
        }
        return buyCardMessage;
    }

    //@Damiano Nardone
    //this method created the following message for the server to pass on to the client
    //when the client plays a card so the actionValue is updated
    //play@PlayerName1@village@actionValue,0
    public String playCardMessage() {

        String playCardMessage = "play@" + playerList.get(getCurrentPlayer()).getName() + "@" + actionCardPlayed + "@actionValue," + playerList.get(getCurrentPlayer()).getActions();
        return playCardMessage;
    }

    //@Damiano Nardone
    //this method checks if certain conditions are met that the game is over
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

    //@Damiano Nardone
    //this method calculates the victoryPoints from the amount of province, estate, duchy and curse in the playerDeck and DiscardDeck
    //TODO MAKE THIS ALSO TAKE INTO ACCOUNT DISCARD DECK
    public void calculateVictoryPoints() {
                int i = 0;
        int playerSize = playerList.size();

        while (i < playerSize) {
            int provinceAmount;
            int estateAmount;
            int duchyAmount;
            int curseAmount;



            provinceAmount = playerList.get(i).getPlayerDeck().get(CardName.province) + playerList.get(i).getDiscardDeck().get(CardName.province) + playerList.get(i).getHandDeck().get(CardName.province);

            estateAmount = playerList.get(i).getPlayerDeck().get(CardName.estate)+ playerList.get(i).getDiscardDeck().get(CardName.estate) + playerList.get(i).getHandDeck().get(CardName.estate);

            duchyAmount =playerList.get(i).getPlayerDeck().get(CardName.duchy)+ playerList.get(i).getDiscardDeck().get(CardName.duchy) + playerList.get(i).getHandDeck().get(CardName.duchy);

            curseAmount = playerList.get(i).getPlayerDeck().get(CardName.curse)+ playerList.get(i).getDiscardDeck().get(CardName.curse) + playerList.get(i).getHandDeck().get(CardName.curse);



            int VictoryPoints = (provinceAmount * 6) + (duchyAmount * 3) + estateAmount + (curseAmount * -1);

            playerList.get(i).setVictoryPoints(VictoryPoints);
            i++;
        }
    }


    //@Damiano Nardone
    //this method draws a certain amount of cards for a certain player from his PlayerDeck
    public void drawCards(int amountToDraw, int playerIndex) {
        ArrayList<CardName> cardNames = new ArrayList<>();


        for (CardName cardName : playerList.get(playerIndex).getPlayerDeck().keySet()) {
            int amount = playerList.get(playerIndex).getPlayerDeck().get(cardName);
            if(amount!=0){
                for(int i = 0; i < amount;i++){
                    cardNames.add(cardName);
                }
            }
        }

        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();

        while (cardsDrawn < amountToDraw) {
            cardName = cardNames.get(rand.nextInt(cardNames.size()));

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

    public void clearListOfCardsDrawnForMessage(){
        listOfCardsDrawnForMessage.clear();
    }

    //@Damiano Nardone
    //this method puts Specifically the curse for a certain person into his or her discardDeck
    public void drawCurse(int amountToDraw, int playerIndex) {
        CardName cardName = null;
        int cardsDrawn = 0;
        Random rand = new Random();
        cardName = CardName.curse;

        if (victoryCardList.get(cardName) != 0) {
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

    //@Damiano Nardone
    //this methods check is the PlayerDeck Hashtable is empty and return a boolean if so
    public boolean checkifPlayerDeckisEmpty(int playerindex) {

        boolean hasCards = false;
        for (CardName cardName : playerList.get(playerindex).getPlayerDeck().keySet()) {
            if (playerList.get(playerindex).getPlayerDeck().get(cardName) != 0) {
                hasCards = true;
            }
        }
        return hasCards;
    }

    //@Damiano Nardone
    //When the player deck is empty discardDeck will be put into playerDeck hashtable
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

        return discardMessage(playerIndex);

    }

    //@Damiano Nardone
    //this methods takes a string with the cardName and them takes the necessary actions
    public String buyCard(String cardName) {
        canIPlay = false;

        switch (cardName) { // addBuy methode mit boolean im if statement
            case "gold":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 6) {
                    subtractBuy(1);
                    subtractCoins(6);
                    cardFromHashTableToDiscardDeck(CardName.gold, coinCardList);
                    cardBoughtMessage = "coinCards/gold,";
                    cardBought = "gold";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "silver":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 3) {
                    subtractBuy(1);
                    subtractCoins(3);
                    cardFromHashTableToDiscardDeck(CardName.silver, coinCardList);
                    cardBoughtMessage = "coinCards/silver,";
                    cardBought = "silver";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "copper":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1) {
                    subtractBuy(1);
                    cardFromHashTableToDiscardDeck(CardName.copper, coinCardList);
                    cardBoughtMessage = "coinCards/copper,";
                    cardBought = "copper";
                } else return "Not@ enough Buys left!";
                break;
            case "province":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 8) {
                    subtractBuy(1);
                    subtractCoins(8);
                    cardFromHashTableToDiscardDeck(CardName.province, victoryCardList);
                    cardBoughtMessage = "victoryCards/province,";
                    cardBought = "province";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "duchy":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.duchy, victoryCardList);
                    cardBoughtMessage = "victoryCards/duchy,";
                    cardBought = "duchy";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "estate":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 2) {
                    subtractBuy(1);
                    subtractCoins(2);
                    cardFromHashTableToDiscardDeck(CardName.estate, victoryCardList);
                    cardBoughtMessage = "victoryCards/estate,";
                    cardBought = "estate";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "village":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 3) {
                    subtractBuy(1);
                    subtractCoins(3);
                    cardFromHashTableToDiscardDeck(CardName.village, actionCardList);
                    cardBoughtMessage = "actionCards/village,";
                    cardBought = "village";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "woodcutter":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 3) {
                    subtractBuy(1);
                    subtractCoins(3);
                    cardFromHashTableToDiscardDeck(CardName.woodcutter, actionCardList);
                    cardBoughtMessage = "actionCards/woodcutter,";
                    cardBought = "woodcutter";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "workshop":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 3) {
                    subtractBuy(1);
                    subtractCoins(3);
                    cardFromHashTableToDiscardDeck(CardName.workshop, actionCardList);
                    cardBoughtMessage = "actionCards/workshop,";
                    cardBought = "workshop";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "smithy":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 4) {
                    subtractBuy(1);
                    subtractCoins(4);
                    cardFromHashTableToDiscardDeck(CardName.smithy, actionCardList);
                    cardBoughtMessage = "actionCards/smithy,";
                    cardBought = "smithy";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "councilroom":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.councilroom, actionCardList);
                    cardBoughtMessage = "actionCards/councilroom,";
                    cardBought = "councilroom";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "festival":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.festival, actionCardList);
                    cardBoughtMessage = "actionCards/festival,";
                    cardBought = "festival";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "laboratory":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.laboratory, actionCardList);
                    cardBoughtMessage = "actionCards/laboratory,";
                    cardBought = "laboratory";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "witch":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.witch, actionCardList);
                    cardBoughtMessage = "actionCards/witch,";
                    cardBought = "witch";
                } else return "Not @enough Coins or Buys left!";
                break;
            case "chancellor":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 3) {
                    subtractBuy(1);
                    subtractCoins(3);
                    cardFromHashTableToDiscardDeck(CardName.chancellor, actionCardList);
                    cardBoughtMessage = "actionCards/chancellor,";
                    cardBought = "chancellor";
                } else return "Not@ enough Coins or Buys left!";
                break;
            case "market":
                if (playerList.get(getCurrentPlayer()).getBuy() >= 1 && playerList.get(getCurrentPlayer()).getCoins() >= 5) {
                    subtractBuy(1);
                    subtractCoins(5);
                    cardFromHashTableToDiscardDeck(CardName.market, actionCardList);
                    cardBoughtMessage = "actionCards/market,";
                    cardBought = "market";
                } else return "Not@ enough Coins or Buys left!";
                break;
            default:
                return "You@ cannot buy Curse Cards you fool!";

        }
        return buyCardMessage();
    }

    //@Damiano Nardone
    //this method takes specific cards for specific Hashtables to put into the Players DiscardDeck
    public void cardFromHashTableToDiscardDeck(CardName cardName, Hashtable<CardName, Integer> list) {
        System.out.println(list.toString());
        if (list.containsKey(cardName) && list.get(cardName) != 0) {
            int currentCount = list.get(cardName);
            list.put(cardName, currentCount - 1);
            if (playerList.get(getCurrentPlayer()).getDiscardDeck().containsKey(cardName)) {
                currentCount = playerList.get(getCurrentPlayer()).getDiscardDeck().get(cardName);
            } else {
                currentCount = 0;
            }
            playerList.get(getCurrentPlayer()).getDiscardDeck().put(cardName, currentCount + 1);
        }
    }

    //@Damiano Nardone
    //this method is used when the client wishes to play the treasures in order to update coins
    public String playTreasures() {
        canIPlay = false;
        Boolean areThereTreasures = false;
        Hashtable<CardName, Integer> tempHandDeck = playerList.get(getCurrentPlayer()).getHandDeck();

        for (Map.Entry<CardName, Integer> entry : tempHandDeck.entrySet()) {
            switch (entry.getKey().toString()) {

                case "gold":
                    areThereTreasures = true;
                    playGold(entry.getValue());
                    break;

                case "silver":
                    areThereTreasures = true;
                    playSilver(entry.getValue());
                    break;

                case "copper":
                    areThereTreasures = true;
                    playCopper(entry.getValue());
                    break;
            }
        }

        if (areThereTreasures) {
            return playTreasureMessage();
        } else return "No@ Coins on Hand!";
    }

    //@Damiano Nardone
    //this method takes a certain CardName and calls that cardsMethod
    public String playCard(String cardName) {

        if (canIPlay) {
            if (playerList.get(getCurrentPlayer()).getActions() == 0) {
                return "Not@ enough Actions left!";
            }
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
        } else return "Playing@ Phase is over, you are already in Buying Phase!";
    }

    //@Damiano Nardone
    //subtracs 1 action and add 2 actions
    public void playVillage() {
        subtractAction(1);
        addAction(2);
    }

    //@Damiano Nardone
    //subtract 1 action add 1 buy add 2 coins to player
    public void playWoodcutter() {
        subtractAction(1);
        addBuy(1);
        addCoins(2);
    }

    //@Damiano Nardone
    //subtract 1 action add 4 coins add 1 buy
    public void playWorkshop() {
        subtractAction(1);
        addCoins(4);
        addBuy(1);
    }

    //@Damiano Nardone
    //subtract 1 action
    public void playSmithy() {
        subtractAction(1);
    }


    //@Damiano Nardone
    //subtract 1 action add 1 buy
    public void playCouncilRoom() {
        subtractAction(1);
        addBuy(1);
    }

    //@Damiano Nardone
    //subtract 1 action add 2 action add 1 buy add 2 coins
    public void playFestival() {
        subtractAction(1);
        addAction(2);
        addBuy(1);
        addCoins(2);
    }

    //@Damiano Nardone
    //subtract 1 action add 1 action
    public void playLaboratory() {
        subtractAction(1);
        addAction(1);
    }

    //@Damiano Nardone
    //subtract 1 action
    public void playWitch() {
        subtractAction(1);

    }

    //@Damiano Nardone
    //subtract 1 action add 2 coins
    public void playChancellor() {
        subtractAction(1);
        addCoins(2);
    }

    //@Damiano Nardone
    //subtract 1 action add 1 coins add 1 buy add 1 action
    public void playMarket() {
        subtractAction(1);
        addCoins(1);
        addBuy(1);
        addAction(1);
    }

    //@Damiano Nardone
    //add coin Value multipled by amount
    public void playGold(int amountOfGold) {
        addCoins(3 * amountOfGold);
    }

    //@Damiano Nardone
    //add coin Value multipled by amount
    public void playSilver(int amountOfSilver) {
        addCoins(2 * amountOfSilver);
    }

    //@Damiano Nardone
    //add coin Value multipled by amount
    public void playCopper(int amountOfCopper) {
        addCoins(1 * amountOfCopper);
    }

    String getGameName() {
        return this.gameName;
    }

    //@Damiano Nardone
    //subtract Action in the Player class
    private void subtractAction(int actionPoints) {
        playerList.get(getCurrentPlayer()).setActionPoints(actionPoints, false);
    }

    public int getCurrentPlayer() {
        int currentP = turnCount % (playerList.size());
        LogHandling.logOnFile(Level.INFO, "currentPlayer at index: "+currentP);
        return currentP; //TODO IS THIS ALRIGHT
    }

    //@Damiano Nardone
    //add action in the player class
    private void addAction(int actionPoints) {
        playerList.get(getCurrentPlayer()).setActionPoints(actionPoints, true);
    }

    //@Damiano Nardone
    //add buys in tht player class
    private void addBuy(int buyingPoints) {
        playerList.get(getCurrentPlayer()).setBuyingPoints(buyingPoints, true);
    }

    //@Damiano Nardone
    //subtract buys in the player class
    private void subtractBuy(int buyingPoints) {
        playerList.get(getCurrentPlayer()).setBuyingPoints(buyingPoints, false);
    }

    //@Damiano Nardone
    //add coins in the player class
    private void addCoins(int coins) {
        playerList.get(getCurrentPlayer()).setCoins(coins, true);
    }

    //@Damiano Nardone
    //subtract coins from player class
    private void subtractCoins(int coins) {
        playerList.get(getCurrentPlayer()).setCoins(coins, false);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}