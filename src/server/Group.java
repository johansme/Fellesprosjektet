package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Group extends shared.Group {
	
	public static boolean createGroup(Group g) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO Group_ VALUES(0, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, g.getName());
			if(g.getParent() != 0) {
				stm.setInt(2, g.getParent());
			} else {
				stm.setNull(2, java.sql.Types.INTEGER);
			}
			stm.setInt(3, g.getCreatedBy());
			
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
	
	public static boolean isUserInGroup(int gid, int uid) {
		DBConnection db = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		boolean exists = true;
		try {
			db = new DBConnection();
			final String stm_str = "SELECT 1 FROM GroupMember WHERE groupid=? AND userid=?";
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
			stm.setInt(2, uid);
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
}
