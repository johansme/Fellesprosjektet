package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Invitation extends shared.Invitation {
	public static ArrayList<Invitation> getInvitationsForUser(int uid) {
		ArrayList<Invitation> list = new ArrayList<Invitation>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT appointmentid,accepted,hidden,dirty FROM Invitation WHERE userid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, uid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				Invitation i = new Invitation();
				i.aid = rs.getInt("appointmentid");
				i.accepted = rs.getBoolean("accepted");
				i.hidden = rs.getBoolean("hidden");
				i.dirty = rs.getBoolean("dirty");
				list.add(i);
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
	
	public static boolean inviteUser(int aid, int uid, Date alert) {
		boolean result = true;
		DBConnection db = null;
		final String stm_str = "INSERT INTO Invitation VALUES(?, ?, ?, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, uid);
			stm.setInt(2, aid);
			stm.setBoolean(3, false);
			stm.setBoolean(4, false);
			stm.setBoolean(5, true);
			stm.setTimestamp(6, new Timestamp(alert.getTime() - 60 * 60 * 1000));			
			stm.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			result = false;
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return result;
	}
	
	public static boolean inviteGroup(int aid, int gid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO GroupInvitation VALUES(?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
			stm.setInt(2, aid);		
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
	
	public static boolean removeUser(int aid, int uid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM Invitation WHERE userid=? AND appointmentid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, uid);
			stm.setInt(2, aid);		
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

	public static boolean removeGroup(int aid, int gid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM GroupInvitation WHERE groupid=? AND appointmentid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
			stm.setInt(2, aid);		
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
	
	public static ArrayList<Integer> getInvitationsForGroup(int gid) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		DBConnection db = null;
		final String str_fmt = "SELECT appointmentid FROM GroupInvitation WHERE groupid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, gid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				int i;
				i = rs.getInt("appointmentid");
				list.add(i);
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
	
	public static ArrayList<Integer> getInvitationsForAppointment(int aid) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		DBConnection db = null;
		final String str_fmt = "SELECT userid FROM Invitation WHERE appointmentid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, aid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				int i;
				i = rs.getInt("userid");
				list.add(i);
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
	
	public static ArrayList<Integer> getGroupInvitationsForAppointment(int aid) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		DBConnection db = null;
		final String str_fmt = "SELECT groupid FROM GroupInvitation WHERE appointmentid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, aid);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				int i;
				i = rs.getInt("groupid");
				list.add(i);
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
	
	public static boolean dirtify(int aid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "UPDATE Invitation SET dirty=TRUE WHERE appointmentid=?";
		
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
	
	public static boolean clean(int uid, int aid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "UPDATE Invitation SET dirty=FALSE WHERE userid=? AND appointmentid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, uid);
			stm.setInt(2, aid);
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
	
	public static boolean alertify(int aid, Date alert) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "UPDATE Invitation SET alarm=? WHERE appointmentid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setTimestamp(1, new Timestamp(alert.getTime() + 60 * 60 * 1000));
			stm.setInt(2, aid);
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
	
	public static boolean isUserInvited(int aid, int uid) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		boolean exists = true;
		try {
			db = new DBConnection();
			final String stm_str = "SELECT 1 FROM Invitation WHERE userid=? AND appointmentid=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, uid);
			stm.setInt(2, aid);
			stm.execute();
			rs = stm.getResultSet();
			exists = rs.first();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return exists;
	}
	
	public static void markSent(int aid, int uid) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			final String stm_str = "UPDATE Invitation SET alarm=NULL WHERE userid=? AND appointmentid=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, uid);
			stm.setInt(2, aid);
			stm.execute();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public static void setHidden(int aid, int uid, boolean hidden) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			final String stm_str = "UPDATE Invitation SET hidden=? WHERE userid=? AND appointmentid=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setBoolean(1, hidden);
			stm.setInt(2, uid);
			stm.setInt(3, aid);
			stm.execute();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public static void setAttending(int aid, int uid, boolean attending) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			final String stm_str = "UPDATE Invitation SET accepted=? WHERE userid=? AND appointmentid=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setBoolean(1, attending);
			stm.setInt(2, uid);
			stm.setInt(3, aid);
			stm.execute();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public static class Alert {
		public int uid;
		public int aid;
		
		public Alert(int uid, int aid) {
			this.uid = uid;
			this.aid = aid;
		}
	}
	
	public static ArrayList<Alert> getDueAlerts() {
		ArrayList<Alert> result = new ArrayList<>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT userid,appointmentid FROM Invitation WHERE alarm<=NOW()";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				Alert a = new Alert(rs.getInt("userid"), rs.getInt("appointmentid"));
				result.add(a);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return result;
	}
	
	public static int isComing(int uid, int aid) {
		int result = -1;
		
		DBConnection db = null;
		final String str_fmt = "SELECT accepted FROM Invitation WHERE userid=? AND appointmentid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, uid);
			stm.setInt(2, aid);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				Object b = rs.getObject(1);
				if(b == null)   result = -1;
				if(!(boolean)b) result = +0;
				else            result = +1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return result;
	}
}