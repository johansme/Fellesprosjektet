package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class Appointment extends shared.Appointment {

	public Appointment(int id) {
		DBConnection db = null;
		final String str_fmt = "SELECT * FROM Appointment WHERE id=?";
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
				location = rs.getString("location");
				description = rs.getString("description");
				start = rs.getTime("starttime");
				end = rs.getTime("endtime");
				modified = rs.getTime("lastmodified");
				creator = rs.getInt("createdby");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}

	public static int createAppointment(Appointment a) {
		DBConnection db = null;
		int id = 0;
		final String stm_str = "INSERT INTO Appointment VALUES(0, ?, ?, ?, ?, NOW(), ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, a.location);
			stm.setString(2, a.description);
			stm.setTime(3, new java.sql.Time(a.start.getTime()));
			stm.setTime(4, new java.sql.Time(a.end.getTime()));
			stm.setInt(5, a.creator);
			stm.executeUpdate();
			ResultSet keys = stm.getGeneratedKeys();
			if(keys.next()) {
				id = (int)keys.getLong(1);
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return id;
	}
	
	public static boolean changeAppointment(Appointment a) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "UPDATE Appointment SET location=?,description=?,starttime=?,endtime=?,lastmodified=NOW() WHERE id=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, a.location);
			stm.setString(2, a.description);
			stm.setTime(3, new java.sql.Time(a.start.getTime()));
			stm.setTime(4, new java.sql.Time(a.end.getTime()));
			stm.setInt(5, a.id);
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
	
	public static boolean deleteAppointment(int id) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM Appointment WHERE id=?";
		
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
	
	public static ArrayList<Integer> getAppointmentsFromCreator(int uid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id FROM Appointment WHERE createdby=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, uid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {		
				list.add(rs.getInt("id"));
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
	
	public User getCreatorUser() {
		try { return new User(creator);	}
		catch(SQLException e) { return null; }
	}
	
	public Appointment() {
		
	}
}
