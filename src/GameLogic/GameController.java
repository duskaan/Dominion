package GameLogic;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    //private StringArray inputMessage;
    private GameModel gameModel;
    private Game game;

    public GameController(GameModel gameModel, Game game) {
        this.game = game;
        this.gameModel = gameModel;
    }

    //@Damiano
    //this starts the game inits the game according to amount of cards in game
    public void startGame(int cardsInGame) {
        gameModel.init(cardsInGame);

        for (int i = 0; i < gameModel.getPlayerList().size(); i++) {
            gameModel.drawCards(5, i);
            game.setResponseMessage(gameModel.initCardsMessage(i));
        }

    }

    //@Damiano
    //this reads messages from the server and according to what is says follows out the actions required
    //for playTreasure, play normal Card, buy normal card, endTurn
    //and some special cards require additional actions besides of the one done in the play method in the GameModel
    // which are done in the second switch statement
    public void readMessage(String message) {
        ArrayList<String> firstSplit = splitMessage(message, "@");
        ArrayList<ArrayList<String>> secondSplit = new ArrayList<>();

        for (int i = 0; i < firstSplit.size(); i++) {


            switch (firstSplit.get(i)) {


                case "playTreasure":
                    game.setResponseMessage(gameModel.playTreasures());
                    break;

                case "endTurn":
                    gameModel.endTurn();
                    game.setResponseMessage(gameModel.endTurnMessage());
                    if (gameModel.isGameOver()) {
                        game.setResponseMessage(gameModel.endGameMessage());
                    }

                    for (int y = 0; y <= 5; y++) {
                        if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                            game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                        }
                        if (y <= 4) {
                            gameModel.drawCards(1, gameModel.getCurrentPlayer());
                        }
                    }

                    game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
                    gameModel.turnCount();
                    break;

                case "buy":
                    game.setResponseMessage(gameModel.buyCard(firstSplit.get(i + 1)));

                    break;

                case "play":
                    game.setResponseMessage(gameModel.playCard(firstSplit.get(i + 1)));
                    if (gameModel.canIPlay) {
                        switch (firstSplit.get(i + 1)) {
                            case "village":
                            case "market":
                                for (int y = 0; y <= 1; y++) {
                                    if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                                        game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                    }
                                    if (y <= 0) {
                                        gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                    }
                                }
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));

                                break;
                            case "smithy":
                                drawThreeCards();
                                /*for (int y = 0; y <= 3; y++) {
                                    if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                                        game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                    }
                                    if (y <= 2) {
                                        gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                    }
                                }*/
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));

                                break;
                            case "laboratory":
                                drawTwoCards(); //todo two cards
                                break;

                            case "witch":
                                drawTwoCards();
                                for (int z = 0; z < gameModel.getPlayerList().size(); z++) {
                                    if (z != gameModel.getCurrentPlayer()) {
                                        gameModel.drawCurse(z);
                                        game.setResponseMessage(gameModel.drawCardMessageWithIndex(z));

                                    }
                                }
                                break;


                            case "councilroom":
                                for (int y = 0; y <= 4; y++) {
                                    if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                                        game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                    }
                                    if (y <= 3) {
                                        gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                    }

                                }
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));

                                for (int z = 0; z < gameModel.getPlayerList().size(); z++) {
                                    if (gameModel.checkifPlayerDeckisEmpty(z)) {
                                        game.setResponseMessage(gameModel.discardDecktoPlayerDeck(z));
                                    }
                                    if (z != gameModel.getCurrentPlayer()) {
                                        gameModel.drawCards(1, z);
                                        game.setResponseMessage(gameModel.drawCardMessageWithIndex(z));


                                    }
                                    if (gameModel.checkifPlayerDeckisEmpty(z)) {
                                        game.setResponseMessage(gameModel.discardDecktoPlayerDeck(z));
                                    }
                                }
                                break;
                            case "chancellor":
                                game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                break;
                        }
                    }
                    break;
            }

        }
    }



    //@Damiano
    //this splits messages according to a delimiter
    private ArrayList<String> splitMessage(String message, String delimiter) {
        ArrayList<String> inputMessage = new ArrayList<>(Arrays.asList(message.split(delimiter)));
        return inputMessage;
    }

    //@Damiano
    //this draws two cards to for the player checks if has enough cards if not does DiscardDecktoPlayerDeck
    //and checks afterwards again
    private void drawTwoCards() {
        for (int i = 0; i <= 2; i++) {
            if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
            }
            if (i <= 1) {
                gameModel.drawCards(1, gameModel.getCurrentPlayer());

            }
        }
        game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
    }

    //@Damiano
    //this draws three cards to for the player checks if has enough cards if not does DiscardDecktoPlayerDeck
    //and checks afterwards again
    private void drawThreeCards() {
        for (int i = 0; i <= 3; i++) {
            if (gameModel.checkifPlayerDeckisEmpty(gameModel.getCurrentPlayer())) {
                game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
            }
            if (i <= 2) {
                gameModel.drawCards(1, gameModel.getCurrentPlayer());

            }
        }
        game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
    }

}
