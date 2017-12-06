package GameLogic;

import javafx.beans.property.SimpleStringProperty;

public class GameController {
    private GameModel gameModel;
    private SimpleStringProperty gameResponseMessage;

    public GameController(GameModel gameModel) {

        this.gameModel = gameModel;
        this.gameResponseMessage = new SimpleStringProperty();
    }

    public void startGame() { gameModel.init(); }
}
