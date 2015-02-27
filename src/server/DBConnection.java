package server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static final String USER = "einh_test";
	private static final String PASS = "test";
	private static final String DB = "einh_cal";
	
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
