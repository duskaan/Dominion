import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by Tim on 22.09.2017.
 */
public class Tester extends Application{




        @Override public void start(Stage stage) {
            Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));

            stage.setTitle("Welcome to JavaFX!");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }



    public static void connectToServer(){

    }
}
