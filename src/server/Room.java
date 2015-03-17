package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
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
				"(SELECT roomid FROM ReservedFor WHERE (? <= starttime OR ? >= endtime)) AS rr " +
				"ON r.id=rr.roomid WHERE rr.roomid IS NULL";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setTime(1, new Time(end.getTime()));
			stm.setTime(2, new Time(start.getTime()));
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
}
