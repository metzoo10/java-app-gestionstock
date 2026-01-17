package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnexion {
	
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
