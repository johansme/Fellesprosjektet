package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class Group extends shared.Group {
	public Group() {
		
	}
	
	public Group(int gid) throws Exception {
		DBConnection db = null;
		final String str_fmt = "SELECT id,name,parent,createdby FROM Group_ WHERE id=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, gid);
			stm.execute();
			rs = stm.getResultSet();
			if(rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				parent = rs.getInt("parent");
				createdBy = rs.getInt("createdby");
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try{ if(rs != null) rs.close(); } catch(Exception e) {}
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
	}
	
	public static boolean modify(Group g) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "UPDATE Group_ SET name=?,createdby=?,parent=? WHERE id=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setString(1, g.name);
			stm.setInt(2, g.createdBy);
			if(g.parent == 0) {
				stm.setNull(3, java.sql.Types.INTEGER);
			} else {
				stm.setInt(3, g.parent);
			}
			stm.setInt(4, g.id);
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
	
	public static ArrayList<Integer> getChildren(int gid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id FROM Group_ WHERE parent=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, gid);
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
	
	public static ArrayList<Integer> getUsers(int gid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT userid FROM GroupMember WHERE groupid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, gid);
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

	public static ArrayList<Integer> getAllFromUser(int uid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT groupid FROM GroupMember WHERE userid=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, uid);
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
	
	public static ArrayList<Integer> getAllFromCreator(int uid) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		DBConnection db = null;
		final String str_fmt = "SELECT id FROM Group_ WHERE createdby=?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(str_fmt);
			stm.setInt(1, uid);
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
	
	public static int createGroup(Group g) {
		DBConnection db = null;
		int id = 0;
		final String stm_str = "INSERT INTO Group_ VALUES(0, ?, ?, ?)";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, g.getName());
			if(g.getParent() != 0) {
				stm.setInt(2, g.getParent());
			} else {
				stm.setNull(2, java.sql.Types.INTEGER);
			}
			stm.setInt(3, g.getCreatedBy());
			stm.executeUpdate();
			ResultSet keys = stm.getGeneratedKeys();
			if(keys.next()) {
				id = (int)keys.getLong(1);
			}
		} catch(SQLException e) {
			id = 0;
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return id;
	}
	
	public static boolean deleteGroup(int gid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM Group_ WHERE id=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
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
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{ if(stm != null) stm.close(); } catch(Exception e) {}
			db.close();
		}
		return success;
	}
	
	public static boolean removeUser(int gid, int uid) {
		DBConnection db = null;
		boolean success = true;
		final String stm_str = "DELETE FROM GroupMember WHERE groupid=? AND userid=?";
		
		PreparedStatement stm = null;
		try {
			db = new DBConnection();
			stm = db.getConnection().prepareStatement(stm_str);
			stm.setInt(1, gid);
			stm.setInt(2, uid);
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
