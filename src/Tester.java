

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Tester extends Application {
    private BufferedWriter output;
    private BufferedReader input;
    Socket socket;
    Label label;
    String labelText;

    public static void main(String[] args) {
        launch(args);


    }

    public void connect() {
        try {
            ServerSocket s = new ServerSocket();
            int port = s.getLocalPort();

            socket = new Socket("127.0.0.1", 9000);
            System.out.println("Accepted connection : " + socket);
            openResources(socket);

            read();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void openResources(Socket socket) {
        try {
            System.out.println("resources are opened");
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //TODO: messages to client

            //send connected message to client open login window
            //create a map of player

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        new Thread(() -> {
            System.out.println("Starts listening");
            boolean running = true;
            while (running) {
                System.out.println("is listening");
                tryReadMessage(input);

            }
            closeResources();

        }).start();
    }

    //TODO: create or use exception to turn the boolean running into false, so that the thread stops.
    private void tryReadMessage(BufferedReader input) {
        String message;
        System.out.println("try to read message");
        try {
            while ((message = input.readLine()) != null) {
                System.out.println("received first message");
                System.out.println(message);


            }
            System.out.println("get out of it already");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setText(String message) {
        label.setText(message);

    }

    public void send(String message) {
        try {
            output.write(message + "\n");
            output.flush();
            System.out.println("Message is sent" + message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.closeResources();
        }
    }
    //use send standard

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Button btn = new Button("click this");
        TextField textField = new TextField();
        label = new Label("hi there");

        btn.setOnAction(e -> {
            connect();
        });
        Button sendButton = new Button("Send");
        Button sendStandardButton = new Button("Standard message");

        sendButton.setOnAction(e -> {
            String message = textField.getText();

        });
        sendStandardButton.setOnAction(e -> {

            String msg = "SERVER@FAILED@HI@HI";
            send(msg);
        });
        vBox.getChildren().addAll(btn, textField, sendButton, sendStandardButton, label);

        Scene scene = new Scene(vBox, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void closeResources() {
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}