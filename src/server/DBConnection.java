package server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost/";
	private static final String USER = "fp";
	private static final String PASS = "fp";
	private static final String DB = "cal";
	
	Connection c;
	
	public DBConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(URL+DB, USER, PASS);
		} catch(Exception e) {
			e.printStackTrace();
			c = null;
		}
	}
	
	public void close() {
		if(c != null) {
			try {
				c.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		return c;
	}
	
}
