package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User extends shared.User {

	private String password;
	
	public User() {
		
	}
	
	public static boolean createUser(User u, String password) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO User VALUES(0, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, u.surname);
			stm.setString(2, u.name);
			stm.setString(3, u.username);
			stm.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
			stm.setString(5, u.email);
			stm.setBoolean(6, u.admin);
			
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
		User u;
		try { u = new User(username); }
		catch(Exception e) { return false; }
		return removeUser(u.id);
	}
	
	public static boolean checkPassword(String username, String password) {
		User u;
		try { u = new User(username); }
		catch(Exception e) { return false; }
		if(u.password == null) return false;
		else return BCrypt.checkpw(password, u.password);
	}
	
	public static boolean exists(String username) throws SQLException {
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
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return exists;
	}
	
	public User(int id) throws SQLException {
		DBConnection db = null;
		final String str_fmt = "SELECT id,surname,name,username,email,password,admin FROM User WHERE id=?";
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
				admin = rs.getBoolean("admin");
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public User(String username) throws SQLException {
		DBConnection db = null;
		final String str_fmt = "SELECT id,surname,name,username,email,password,admin FROM User WHERE username=?";
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
				admin = rs.getBoolean("admin");
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> list = new ArrayList<User>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id,name,surname,email,admin FROM User";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				User u = new User();
				u.id = rs.getInt("id");
				u.name = rs.getString("name");
				u.surname = rs.getString("surname");
				u.email = rs.getString("email");
				u.admin = rs.getBoolean("admin");
				list.add(u);
			}
		} catch(Exception e) {
			e.printStackTrace();
			list = null;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return list;
}
}
