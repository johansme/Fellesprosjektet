package server;

import java.sql.PreparedStatement;
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
}
