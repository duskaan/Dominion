package Server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Tim on 20.09.2017.
 */
public class ServerGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        //starting point for the application
        //this is where we put the code for the user interface


        //The primaryStage is the top-level container
        primaryStage.setTitle("example Gui");

        //The BorderPane has the same areas laid out as the
        //BorderLayout layout manager
        BorderPane componentLayout = new BorderPane();
        Label lbl = new Label("Ready to connect");
        componentLayout.getChildren().add(lbl);


        //Add the BorderPane to the Scene
        Scene appScene = new Scene(componentLayout, 500, 500);

        //Add the Scene to the Stage
        primaryStage.setScene(appScene);
        primaryStage.show();
    }
}


