package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group extends shared.Group {
	public static ArrayList<Group> getAllGroups() {
		ArrayList<Group> list = new ArrayList<Group>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id,name,parent,createdby FROM Group_";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.execute();
			rs = stm.getResultSet();
			while(rs.next()) {
				Group g = new Group();
				g.id = rs.getInt("id");
				g.name = rs.getString("name");
				g.parent = rs.getInt("parent");
				g.createdBy = rs.getInt("createdby");
				list.add(g);
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
	
	public static boolean addUser(int gid, int uid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "INSERT INTO GroupMember VALUES(?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
			stm.setInt(2, uid);
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
