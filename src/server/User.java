package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id = 0;
	private String name = null;
	private String username = null;
	private String email = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public static boolean createUser(String name, String username, String password, String email) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO Employee VALUES(0, ?, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, name);
			stm.setString(2, username);
			stm.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));
			stm.setString(4, email);
			stm.executeUpdate();
		} catch(SQLException e) {
			success = false;
			System.out.println(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return success;
	}
	
	public static boolean checkPassword(String username, String password) {
		
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String db_pwd = null;
		try {
			db = new DBConnection();
			final String stm_str = "SELECT password FROM Employee WHERE username=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, username);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				db_pwd = rs.getString("password");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		if(db_pwd == null) return false;
		else return BCrypt.checkpw(password, db_pwd);
	}
	
	public static boolean exists(String username) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		boolean exists = true;
		try {
			db = new DBConnection();
			final String stm_str = "SELECT 1 FROM Employee WHERE username=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, username);
			stm.execute();
			rs = stm.getResultSet();
			exists = rs.first();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return exists;
	}
	
	public User(int id) {
		DBConnection db = null;
		final String str_fmt = "SELECT name,username,email FROM Employee WHERE id=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, id);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				name = rs.getString("name");
				username = rs.getString("username");
				email = rs.getString("email");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("username", username);
		obj.put("email", email);
		return obj;
	}
	
	public static void main(String []args) {
		createUser("Nicolas Cage", "nicage", "nicage", "einhov@gmail.com");
	}
}
