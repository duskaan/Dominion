package DataBasePackage;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Tim on 13.09.2017.
 */
public class Database {
    private String login = null;
    private String password = null;
    private Connection con = null;
    private PreparedStatement preparedStatement = null;
    private  Properties prop = new Properties(); // Create Properties-Object
    private ResultSet resultSet = null;
    private static final Database snDatabase= null;

    private final String userName= "userName";
    private final String userPassword= "userPassword";
    private final String gamesPlayed= "gamesPlayed";
    private final String gamesWon= "gamesWon";
    private final String gamesHighScore= "gamesHighScore";

    private Database(){

    }
    public void createConnection() {
        try {
            prop.load(new FileInputStream("config.properties"));
            login = prop.getProperty("login");
            password = prop.getProperty("password");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", login, password);
        } catch (Exception e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    public boolean insert(String insertUserName, String insertUserPassword) {
         //todo close and open connection just once
        boolean successful = false;
        if (search(insertUserName)) { //if username is already present
            return successful;
        }
        successful=tryInsert(insertUserName, insertUserPassword);


        return successful;
    }

    private boolean tryInsert(String insertUserName, String insertUserPassword) {
        String query = "insert into player("+userName+"," +userPassword+","+ gamesPlayed+"," +gamesWon+","+ gamesHighScore+") values(?, ?, ?, ?,?)";

        boolean successful;
        try {
            preparedStatement = con.prepareStatement(query); // Create Prepared Statement
            preparedStatement.setString(1, insertUserName); // Set Parameter 1
            preparedStatement.setString(2, insertUserPassword); // Set Parameter 2
            preparedStatement.setInt(3, 0); // Set Parameter 3
            preparedStatement.setInt(4, 0); // Set Parameter 4
            preparedStatement.setInt(5, 0); // Set Parameter 5
            preparedStatement.executeUpdate(); // Execute Statement
            System.out.println(" Record inserted");
            successful = true;
        } catch (SQLException e) {
            e.printStackTrace();
            successful = false;
        }
        return successful;
    }

    private boolean search(String userName) { //not finished yet
        boolean found = false;
        try {
            resultSet = getResultSet(userName);
            if (resultSet.next()) {
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    public boolean login(String userName, String password) {

        boolean successful = false;
        try {
            resultSet = getResultSet(userName);
            if (resultSet.getString(2).equals(password)) {
                successful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return successful;
        //todo somehow send a message or store the info of the players clientsocket and the players username
        //maybe a class which takes clientthreads and players
    }

    private void closeConnection() {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
            if (con != null)
                con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAfterGame(String inUserName, int highScore, boolean won) {
        int newHighScore;
        resultSet = getResultSet(inUserName);
        try {
            int newGamesPlayed = resultSet.getInt(gamesPlayed) + 1;
            int newGamesWon = resultSet.getInt(gamesWon);
            if(won){
             newGamesWon++;
            }
            if (highScore > resultSet.getInt(gamesHighScore)) {
                newHighScore = highScore;
            } else {
                newHighScore = resultSet.getInt(gamesHighScore);
            }
            update(inUserName, newGamesPlayed, newGamesWon, newHighScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String userName) {
        try {
            String query = "Select * From Player where userName = ? values(?)";
            preparedStatement = con.prepareStatement(query); // Create Prepared Statement
            preparedStatement.setString(1, userName); // Set Parameter 1
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private void update(String userName, int newGamesPlayed, int newGamesWon, int newHighScore) {
        String query = "UPDATE player(gamesPlayed, gamesWon, gamesHighscore) where userName = ? values(?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(query); // Create Prepared Statement
            preparedStatement.setInt(1, newGamesPlayed); // Set Parameter 3
            preparedStatement.setInt(2, newGamesWon); // Set Parameter 4
            preparedStatement.setInt(3, newHighScore); // Set Parameter 5
            preparedStatement.setString(4, userName);
            preparedStatement.executeUpdate(); // Execute Statement

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Database getDatabase(){
        if(snDatabase==null){
            return new Database();
        }
        else {
            return snDatabase;
        }
    }
}

