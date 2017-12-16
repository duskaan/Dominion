package GameLogic;

import javafx.beans.property.SimpleStringProperty;

import java.util.Observable;


public class Game extends Observable{

    private GameModel gameModel;
    private GameController gameController;

    private SimpleStringProperty gameResponseMessage;
    private String responseMessage;
    private String inputMessage;
    private int cardsInGame;
    private String[] playerNames;



    public Game(String gameName, int cardsInGame, String... playerNames) {
        this.cardsInGame = cardsInGame;
        this.playerNames = playerNames;
        responseMessage= new String();
        this.gameModel = new GameModel(gameName, gameResponseMessage);
        this.gameController = new GameController(gameModel,this);
        this.gameResponseMessage = new SimpleStringProperty(); //gaht das??
    }

    public void createGame(String[] playerNames ){
        int playerCount = playerNames.length;

        for ( int i = 0 ; i < playerNames.length; i++){
            gameModel.addPlayer(playerNames[i],playerCount);
        }

    }

    public void startGame(int cardsInGame) {
        createGame(playerNames);
        gameController.startGame(cardsInGame);    }

    public String getGameName() {
        return gameModel.getGameName();
    }

    public void readMessage(String message) {
       // gameController.readMessage(message)
        this.inputMessage=message;
        gameController.readMessage(message);
    }

    public void setResponseMessage(String responseMessage){
        gameResponseMessage.set(responseMessage);

    }
    public String getResponseMessage(){
        return responseMessage;
    }
    public SimpleStringProperty getGameResponseMessage(){
        return gameResponseMessage;
    }


}
