package Server;

import Database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static Server.Server.createDatabaseConnection;
import static Server.Server.initServer;

/**
 * Created by Tim on 20.09.2017.
 */
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ServerGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        //starting point for the application
        //this is where we put the code for the user interface


        //The primaryStage is the top-level container
        primaryStage.setTitle("ServerGUI");

        //The BorderPane has the same areas laid out as the
        //BorderLayout layout manager
        BorderPane componentLayout = new BorderPane();

        VBox userPassBox = new VBox(15);
        userPassBox.setAlignment(Pos.CENTER);

        Label userNameLabel = new Label("Database userName");
        TextField userNameField = new TextField();
        userNameField.setMaxWidth(200);


        Label passwordLabel = new Label("Database password");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);

        userPassBox.getChildren().addAll(userNameLabel, userNameField, passwordLabel, passwordField);

        Button loginButton = new Button("login");

        loginButton.setOnAction(e -> {
            listenForConnection();
            createDatabaseConnection(userNameField.getText(), passwordField.getText());
            initServer();

        });

        componentLayout.getChildren().addAll(userPassBox, loginButton);


        //Add the BorderPane to the Scene


        //Add the Scene to the Stage
        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton);
        VBox content = new VBox(30);
        content.getChildren().addAll(userPassBox, buttonBox);
        content.setMaxWidth(300);
        content.setAlignment(Pos.CENTER);
        BorderPane parent = new BorderPane();
        BorderPane.setAlignment(content, Pos.CENTER);
        BorderPane.setMargin(content, new Insets(100));
        parent.setCenter(content);
        Scene appScene = new Scene(parent, 500, 500);
        primaryStage.setScene(appScene);
        primaryStage.show();
    }
    private void listenForConnection() {
        Database.getIsConnected().addListener((observable, oldValue, newValue)-> showAlert(newValue));
    }
    private void showAlert(boolean isConnected) {

        Alert alert = new Alert(Alert.AlertType.NONE); //todo delete unnecessary logs
        //todo get chat to work
        //todo write javadoc
        //todo current player in the screen
        //todo victory points of others


        if (isConnected) {

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Database Connection Successful");
            alert.setContentText("please start the clients");
            alert.showAndWait();
        }
        if(!isConnected){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Failed");
            alert.setContentText("Please Try again");
            alert.showAndWait();
        }

    }
}



