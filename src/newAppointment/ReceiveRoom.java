package newAppointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import calendar.Appointment;
import server.*;


public class ReceiveRoom extends DBConnection{
	
	 
	List<String> roomList = new ArrayList<String>();
	Appointment app = new Appointment();
	
	public Connection recvConnection(){

		DBConnection db = new DBConnection();
		Connection c = db.getConnection();
		return c; 

	}

	//return room values MeetingRoom(id, name, capacity)
	public void roomValues(ResultSet rs) throws SQLException{
		
		if(rs == null)
		{
			return;
		}
		
		try{
			while(rs.next()){
				
				String roomName = rs.getString(2);
				String roomCapacity = rs.getString(3);
				if(roomName != "" && roomCapacity != "" && roomName != null && roomCapacity != null)
				{
					String item = roomName + " (" + roomCapacity + ")";
					roomList.add(item);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("DB could not get room values");
		}
		app.setRoomList(roomList);

	}
	
	public void makeRoomQuery()
	{
		
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
	
	public void setRoomList(List<String> roomList)
	{
		this.roomList = roomList;
	}
	public List<String> getRoomList(){
		
		return this.roomList; 
	}

}
