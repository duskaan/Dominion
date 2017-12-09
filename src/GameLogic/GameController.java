package GameLogic;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {
    //private StringArray inputMessage;
    private GameModel gameModel;
    private SimpleStringProperty gameResponseMessage;
    private String[] inputMessage;

    public GameController(GameModel gameModel) {

        this.gameModel = gameModel;
        this.gameResponseMessage = new SimpleStringProperty();
    }

    public void startGame() { gameModel.init(); }

    public void readMessage(String message) {
        ArrayList<String> firstSplit = splitMessage(message,"@");
        ArrayList<ArrayList<String>> secondSplit = new ArrayList<>();
        ArrayList<ArrayList<String>> thirdSplit = new ArrayList<>();

        for (int i = 0; i <= firstSplit.size(); i++){
            secondSplit.add(i,splitMessage(firstSplit.get(i),"/"));

            switch(secondSplit.get(i).get(0)){

                case "playTreasure": gameModel.playTreasures();
                                    break;

                case "endTurn": gameModel.endTurn();
                                    break;

            }

        //    case "buy": gameModel.buyCard(secondSplit.get(i).get());
         //   case "play":    gameModel.playCard("sldfa");

        }
    }

    private ArrayList<String> splitMessage(String message, String delimiter) {
        ArrayList<String> inputMessage = new ArrayList<>(Arrays.asList(message.split(delimiter)));
        return inputMessage;
    }
}
