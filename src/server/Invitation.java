package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static boolean inviteUser(int aid, int uid) {
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
			stm.setNull(6, java.sql.Types.DATE);			
			stm.executeUpdate();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
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
}
