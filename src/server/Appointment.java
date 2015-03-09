package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
				id = rs.getInt("id");
				location = rs.getString("location");
				description = rs.getString("description");
				start = rs.getDate("starttime");
				end = rs.getDate("endtime");
				modified = rs.getDate("lastmodified");
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
	
	
	public User getCreatorUser() {
		try { return new User(creator);	}
		catch(SQLException e) { return null; }
	}
}
