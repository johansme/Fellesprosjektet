package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
}
