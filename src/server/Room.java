package server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Room extends shared.Room {
	
	public Room() {
		
	}
	
	public Room(int rid) {
		DBConnection db = null;
		final String str_fmt = "SELECT * FROM MeetingRoom WHERE id=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, rid);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				capacity = rs.getInt("capacity");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public static ArrayList<Integer> getAppointmentsForRoom(int rid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT appointmentid FROM ReservedFor WHERE roomid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, rid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				list.add(rs.getInt(1));
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
	
	public static int getForAppointment(int aid) {
		int i = 0;
		DBConnection db = null;
		final String str_fmt = "SELECT roomid FROM ReservedFor WHERE appointmentid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, aid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {		
				i = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
			i = 0;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return i;
	}
	
	public static boolean create(Room r) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO MeetingRoom VALUES(0, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, r.getName());
			stm.setInt(2, r.getCapacity());			
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
	
	public static boolean remove(int id) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM MeetingRoom WHERE id=?";
		
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
	
	public static ArrayList<Room> getAllRooms() {
		ArrayList<Room> list = new ArrayList<Room>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id,name,capacity FROM MeetingRoom";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				Room r = new Room();
				r.id = rs.getInt("id");
				r.name = rs.getString("name");
				r.capacity = rs.getInt("capacity");
				list.add(r);
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
	
	public static ArrayList<Room> getAvailable(Date start, Date end) {
		ArrayList<Room> list = new ArrayList<Room>();
		
		// TODO: Test this
		DBConnection db = null;
		final String str_fmt = 
				"SELECT r.id,r.name,r.capacity FROM " +
				"MeetingRoom AS r LEFT JOIN " +
				"(SELECT roomid FROM ReservedFor WHERE NOT(? > endtime || ? < starttime)) AS rr " +
				"ON r.id=rr.roomid WHERE rr.roomid IS NULL";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setTimestamp(1, new Timestamp(start.getTime()));
			stm.setTimestamp(2, new Timestamp(end.getTime()));
			System.out.println(stm.toString());
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				Room r = new Room();
				r.id = rs.getInt("r.id");
				r.name = rs.getString("r.name");
				r.capacity = rs.getInt("r.capacity");
				list.add(r);
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
	
	// TODO: Test this
	// TODO: Is this properly locked?
	public static boolean reserve(int aid, int rid, Date start, Date end) throws IOException {
		DBConnection db = null;
		ResultSet rs = null;
		boolean success = true;
//		final String stm_lock = "LOCK TABLE ReservedFor IN EXCLUSIVE MODE";
		final String stm_poll = "SELECT 1 FROM ReservedFor WHERE (roomid=? AND NOT(? > endtime || ? < starttime))";
		final String stm_ins = "INSERT INTO ReservedFor VALUES(?, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			db.getConnection().setAutoCommit(false);
			stm = db.getConnection().prepareStatement(stm_poll);
			stm.setInt(1, rid);
			stm.setTimestamp(2,  new Timestamp(end.getTime()));
			stm.setTimestamp(3, new Timestamp(start.getTime()));
			rs = stm.executeQuery();
			if(rs.next()) {
				System.out.println("There is a resultset");
				success = false;
			} else {
				stm.close();
				stm = db.getConnection().prepareStatement(stm_ins);
				stm.setInt(1, aid);
				stm.setInt(2, rid);
				stm.setTimestamp(3, new Timestamp(start.getTime()));
				stm.setTimestamp(4, new Timestamp(end.getTime()));
				stm.executeUpdate();
			}
			db.getConnection().commit();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			try { db.getConnection().rollback(); } catch(Exception e2) { e2.printStackTrace(); }
			success = false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			db.close();
		}
		return success;
	}
	
	public static boolean cancel(int aid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM ReservedFor WHERE appointmentid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, aid);
			stm.executeUpdate();
		} catch(SQLException e) {
			success = false;
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return success;
	}
}
