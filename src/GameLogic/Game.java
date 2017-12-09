package GameLogic;

import javafx.beans.property.SimpleStringProperty;

import java.util.Observable;


public class Game extends Observable{

    private GameModel gameModel;
    private GameController gameController;

    private SimpleStringProperty gameResponseMessage;
    private String responseMessage;
    private String inputMessage;


    public Game(String gameName, int cardsInGame, String... playerNames) {
        responseMessage= new String();
        this.gameModel = new GameModel(gameName, gameResponseMessage);
        this.gameController = new GameController(gameModel);
        this.gameResponseMessage = new SimpleStringProperty(); //gaht das??
    }

    public void startGame() { gameController.startGame();    }

    public String getGameName() {
        return gameModel.getGameName();
    }

    public void readMessage(String message) {
       // gameController.readMessage(message)
        this.inputMessage=message;
    }

    public void setResponseMessage(String responseMessage){
        this.responseMessage =responseMessage;
        setChanged();
        notifyObservers(responseMessage);
    }


}
