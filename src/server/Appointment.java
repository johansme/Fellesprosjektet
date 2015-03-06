package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;

public class Appointment {
	private int id = 0;
	private int creator = 0;
	private String description = null;
	private String location = null;
	private Date start = null;
	private Date end = null;
	private Date modified = null;
	
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getModified() {
		return modified;
	}
	
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
				description = rs.getString("description");
				location = rs.getString("location");
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
	
	public User getCreator() {
		try { return new User(creator);	}
		catch(SQLException e) { return null; }
	}
	
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("description", description);
		obj.put("location", location);
		obj.put("start", start.toString());
		obj.put("end", end.toString());
		obj.put("modified", modified.toString());
		return obj;
	}
}
