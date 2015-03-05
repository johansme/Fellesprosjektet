package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id = 0;
	private String name = null;
	private String surname = null;
	private String username = null;
	private String email = null;
	private String password = null;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return name;
	}

	public void setSurname(String name) {
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

	public static boolean createUser(String surname, String name, String username, String password, String email) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO User VALUES(0, ?, ?, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, surname);
			stm.setString(2, name);
			stm.setString(3, username);
			stm.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
			stm.setString(5, email);
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
	
	public static boolean removeUser(int id) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM User WHERE id=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, id);
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
	
	public static boolean removeUser(String username) {
		User u = new User(username);
		return removeUser(u.id);
	}
	
	public static boolean checkPassword(String username, String password) {
		User u = new User(username);
		if(u.password == null) return false;
		else return BCrypt.checkpw(password, u.password);
	}
	
	public static boolean exists(String username) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		boolean exists = true;
		try {
			db = new DBConnection();
			final String stm_str = "SELECT 1 FROM User WHERE username=?";
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
		final String str_fmt = "SELECT id,surname,name,username,email,password FROM User WHERE id=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, id);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				this.id = rs.getInt("id");
				surname = rs.getString("surname");
				name = rs.getString("name");
				username = rs.getString("username");
				email = rs.getString("email");
				password = rs.getString("password");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public User(String username) {
		DBConnection db = null;
		final String str_fmt = "SELECT id,surname,name,username,email,password FROM User WHERE username=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setString(1, username);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				id = rs.getInt("id");
				surname = rs.getString("surname");
				name = rs.getString("name");
				this.username = rs.getString("username");
				email = rs.getString("email");
				password = rs.getString("password");
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
		obj.put("id", id);
		obj.put("name", name);
		obj.put("surname", surname);
		obj.put("username", username);
		obj.put("email", email);
		return obj;
	}
	
	static void CreateTestUsers() {
		createUser("Stalin", "Joseph", "staljo", "staljo", "staljo@sovjet.ru");
		createUser("Stephenson", "Bob", "bobstep", "bobstep", "bob@step.meme");
		createUser("Cage", "Nicolas", "nicage", "nicage", "einhov@gmail.com");
	}
	
	public static void main(String []args) {

	}
}
