package Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataBaseCredentials {
	public static void main(String[] args) {
		Properties prop = new Properties();

		try {
			// Set Properties Values
			prop.setProperty("login", "root");
			prop.setProperty("password", "");

			// Save Properties
			prop.store(new FileOutputStream("config.properties"), null);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
