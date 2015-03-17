package server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Room extends shared.Room {
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
	
	// TODO: Is this properly locked?
	public static boolean reserve(int aid, int rid, Date start, Date end) throws IOException {
		DBConnection db = null;
		ResultSet rs = null;
		boolean success = true;
//		final String stm_lock = "LOCK TABLE ReservedFor IN EXCLUSIVE MODE";
		final String stm_poll = "SELECT 1 FROM ReservedFor WHERE (roomid=? AND NOT (? <= starttime OR ? >= endtime))";
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
}
