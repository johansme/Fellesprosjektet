package newAppointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import server.*;



public class ReceiveRoom extends DBConnection{


	private ObservableList<MenuItem> menuItemList;

	public static Connection recvConnection(){

		DBConnection db = new DBConnection();
		Connection c = db.getConnection();
		return c; 

	}



	//return room values MeetingRoom(id, name, capacity)
	public void roomValues(ResultSet rs) throws SQLException{
		getDBConnection();
		
		if(rs == null)
		{
			return;
		}
		try{
			while(rs.next()){
				
				String roomName = rs.getString(2);
				String roomCapacity = rs.getString(3);
				MenuItem room = new MenuItem(roomName + " " + roomCapacity);
				this.menuItemList.addAll(room);
				System.out.println("Room " + roomName + " has capacity: " + roomCapacity);
				
			}
			
		}catch(Exception e){
			System.err.println("DB could not get room values");
		}

	}

	public void getDBConnection(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = recvConnection();
			// prepare query
			String query = "select id, name, capacity from MeetingRoom";
			// create a statement
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			roomValues(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ObservableList<MenuItem> getRoomValues()
	{
		return menuItemList; 
		
	}

}
