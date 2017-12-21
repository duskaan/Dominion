package GameLogic;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    //private StringArray inputMessage;
    private GameModel gameModel;
    private Game game;

    public GameController(GameModel gameModel,Game game) {
        this.game = game;
        this.gameModel = gameModel;
    }


    public void startGame(int cardsInGame) {
        gameModel.init(cardsInGame);

        for (int i = 0; i < gameModel.getPlayerList().size(); i++) {
            gameModel.drawCards(5, i);
            game.setResponseMessage(gameModel.initCardsMessage(i));
        }

    }

    public void readMessage(String message) {
        ArrayList<String> firstSplit = splitMessage(message, "@");
        ArrayList<ArrayList<String>> secondSplit = new ArrayList<>();

        for (int i = 0; i < firstSplit.size(); i++) {


            switch (firstSplit.get(i)) {



                case "playTreasure":game.setResponseMessage(gameModel.playTreasures());
                                    break;

                case "endTurn":
                    game.setResponseMessage(gameModel.endTurnMessage());
                    if (gameModel.isGameOver()) {
                        game.setResponseMessage(gameModel.endGameMessage());
                    }
                    gameModel.endTurn(); //todo was machsch nach dem gnau?
                    for (int y = 0; y <= 4; y++) {
                        if (gameModel.checkifPlayerDeckisEmpty()) {
                            game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                        }
                        gameModel.drawCards(1, gameModel.getCurrentPlayer());
                        game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
                    }
                    gameModel.turnCount();
                    break;

                case "buy":
                    game.setResponseMessage(gameModel.buyCard(firstSplit.get(i + 1)));

                    break;

                case "play":
                    game.setResponseMessage(gameModel.playCard(firstSplit.get(i + 1)));

                    switch (firstSplit.get(i + 1)) {
                        case "village":
                        case "market":
                            for (int y = 0; y <= 0; i++) {
                                if (gameModel.checkifPlayerDeckisEmpty()) {
                                    game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                }
                                gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
                            }
                            break;
                        case "smithy":
                            for (int y = 0; y <= 2; i++) {
                                if (gameModel.checkifPlayerDeckisEmpty()) {
                                    game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                }
                                gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
                            }
                            break;
                        case "laboratory":
                            drawOneCard();
                            break;

                        case "witch":
                            drawOneCard();
                            for (int z = 0; z < gameModel.getPlayerList().size(); z++) {
                                if (gameModel.checkifPlayerDeckisEmpty()) {
                                    game.setResponseMessage(gameModel.discardDecktoPlayerDeck(z));
                                }
                                if (z != gameModel.getCurrentPlayer()) {
                                    gameModel.drawCurse(1, z);
                                    game.setResponseMessage(gameModel.drawCardMessageWithIndex(z));
                                }
                            }
                            break;


                        case "councilroom":
                            for (int y = 0; y <= 3; i++) {
                                if (gameModel.checkifPlayerDeckisEmpty()) {
                                    game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                                }
                                gameModel.drawCards(1, gameModel.getCurrentPlayer());
                                game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
                            }
                            for (int z = 0; z < gameModel.getPlayerList().size(); z++) {
                                if (gameModel.checkifPlayerDeckisEmpty()) {
                                    game.setResponseMessage(gameModel.discardDecktoPlayerDeck(z));
                                }
                                if (z != gameModel.getCurrentPlayer()) {
                                    gameModel.drawCards(1, z);
                                    game.setResponseMessage(gameModel.drawCardMessageWithIndex(z));
                                }
                            }
                            break;
                        case "chancellor":
                            game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
                            break;
                    }

                    break;
            }

        }
    }

    private ArrayList<String> splitMessage(String message, String delimiter) {
        ArrayList<String> inputMessage = new ArrayList<>(Arrays.asList(message.split(delimiter)));
        return inputMessage;
    }

    private void drawOneCard() {
        for (int y = 0; y <= 1; y++) {
            if (gameModel.checkifPlayerDeckisEmpty()) {
                game.setResponseMessage(gameModel.discardDecktoPlayerDeck(gameModel.getCurrentPlayer()));
            }
            gameModel.drawCards(1, gameModel.getCurrentPlayer());
            game.setResponseMessage(gameModel.drawCardMessageWithIndex(gameModel.getCurrentPlayer()));
        }
    }
}
