package Server;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by Tim on 20.09.2017.
 */
public class LogHandling {
    static final String fileName = "logger.txt";
    static int indexOfNewElement = 2;
    static BufferedWriter writer = null;



    public static void logOnFile(Level level, String message) {

        String date = getDate();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String methodName = stackTraceElements[indexOfNewElement].getMethodName();
        String className = stackTraceElements[indexOfNewElement].getClassName();

        tryWriteOnFile(level, message,methodName ,className , date);
    }

    private static void tryWriteOnFile(Level level, String message, String methodName, String className, String date) {

        try {
            if (writer == null) {
                writer = new BufferedWriter(new FileWriter(fileName));
            }
            writer.write(date +" " + className + " method: " + methodName +  "\n" + level.toString() + ": " + message+"\n");
            System.out.println(message);

        } catch (IOException e) {
        }
    }

    public static String getDate() {
        //TODO: The Date object is outdated. You should use LocalDateTime, LocalTime or LocalDate
        //TODO: Also everyTime you write to file, this creates a new Date object, which uses a lot of memory.
        Date date = new Date();

        DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String text = fmt.format(date);

        return text; //2016/11/16 12:08:43
    }


    public static void closeResources() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
