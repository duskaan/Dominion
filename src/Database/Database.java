package Database;

import Server.LogHandling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Created by Tim on 13.09.2017.
 */
public class Database {
    private String login = null;
    private String password = null;
    private static Connection con=null;
    private PreparedStatement preparedStatement = null;
    private Properties prop = new Properties(); // Create Properties-Object
    private ResultSet resultSet = null;
    private static final Database snDatabase = null;
    private Statement stmt;


    private Database() {

    }

    public void initDatabase() {
        Properties prop = new Properties();

        try {
            // Set Properties Values
            prop.setProperty("login", "java");
            prop.setProperty("password", "1234");

            // Save Properties
            prop.store(new FileOutputStream("config.properties"), null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createConnection() {
        try {
            prop.load(new FileInputStream("config.properties"));
            //login = prop.getProperty("login");
           // password = prop.getProperty("password");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Enter your login for the database");
            login = scanner.next();
            System.out.println("Please Enter your password for the database");
            password = scanner.next();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", login, password);
            createDatabase();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/DominionDatabase?useSSL=false", login, password);
            createTable();
            System.out.println("Is this here?");

        } catch (Exception e) {
            e.printStackTrace();
            closeConnection();
        }
    }



    private void createDatabase() {
        //String dropQuery = "DROP DATABASE if Exists DominionDatabase";
        String query = "CREATE DATABASE DominionDatabase";

        //stmt.executeUpdate(dropQuery);


        try {
            stmt= con.createStatement();
            stmt.executeUpdate(query);
            LogHandling.logOnFile(Level.INFO, "Database DominionDatabase is created");
        } catch (SQLException e) {
            LogHandling.logOnFile(Level.INFO, "Database already exists");
        }
    }

    private void createTable() {
        //String dropQuery = "DROP TABLE IF EXISTS Users;";
        String query = "CREATE TABLE Player (Username varchar(255) NOT NULL, Password varchar(255) NOT NULL, GamesPlayed int(10), GamesWon int(10), Highscore int(10));";

        //stmt.executeUpdate(dropQuery);
        try {
            stmt= con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            LogHandling.logOnFile(Level.INFO, "Database already exists");
        }

    }
    public boolean insert(String insertUserName, String insertUserPassword) {

        boolean successful = false;
        if (!search(insertUserName)) { //if username is already present
            successful = tryInsert(insertUserName, insertUserPassword);
        }


        LogHandling.logOnFile(Level.INFO, "insert "+successful+" Name: "+insertUserName+" Password: "+insertUserPassword);
        return successful;
    }

    private boolean tryInsert(String insertUserName, String insertUserPassword) {
        String query = "INSERT INTO player(userName,password,gamesPlayed,gamesWon,HighScore) VALUES(?, ?, ?, ?,?)";

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
            if (resultSet.next()&&resultSet.getString(2).equals(password)) {
                successful = true;
                LogHandling.logOnFile(Level.INFO,"Login with Name: "+userName+" and Password: "+password + " was successful");
            }else {
                LogHandling.logOnFile(Level.INFO,"Login with Name: "+userName+" and Password: "+password + " failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return successful;
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
            if(resultSet.next()){
            int newGamesPlayed = resultSet.getInt("gamesPlayed") + 1;
            int newGamesWon = resultSet.getInt("gamesWon");
            if (won) {
                newGamesWon++;
            }
            if (highScore > resultSet.getInt("HighScore")) {
                newHighScore = highScore;
            } else {
                newHighScore = resultSet.getInt("HighScore");
            }
            update(inUserName, newGamesPlayed, newGamesWon, newHighScore);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String userName) {
        try {
            String query = "SELECT * FROM player WHERE userName = ?";
            preparedStatement =con.prepareStatement(query); // Create Prepared Statement
            preparedStatement.setString(1, userName); // Set Parameter 1
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String getTopFive() {
        String returnMessage = "";
        try {
            String query = "SELECT * FROM player ORDER BY Highscore DESC LIMIT 5";
            preparedStatement = con.prepareStatement(query); // Create Prepared Statement
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                returnMessage += resultSet.getString("userName") + ";";
                returnMessage += resultSet.getInt("Highscore") + ";";
                returnMessage += resultSet.getInt("gamesPlayed") + ";";
                returnMessage += resultSet.getInt("gamesWon") + ";";
                returnMessage += "/";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnMessage;

    }


    private void update(String userName, int newGamesPlayed, int newGamesWon, int newHighScore) {
        String query = "UPDATE player SET gamesPlayed =?, gamesWon= ?, Highscore=? WHERE userName = ? ";
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

    public static Database getDatabase() {
        if (snDatabase == null) {
            return new Database();
        } else {
            return snDatabase;
        }
    }
}

