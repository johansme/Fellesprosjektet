package newAppointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import server.*;


public class ReceiveRoom extends DBConnection{
	
	 
	private List<Tuple> roomList = new ArrayList<Tuple>();
	
	
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
				int roomCapacity = Integer.valueOf( rs.getString(3));
				if(roomName != "" && roomCapacity != 0 && roomName != null)
				{
					Tuple item = new Tuple(roomName,  roomCapacity );
					roomList.add(item);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("DB could not get room values");
		}
		

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
	
	public void setRoomList(List<Tuple> roomList)
	{
		this.roomList = roomList;
	}
	public List<Tuple> getRoomList(){
		
		return this.roomList; 
	}

}
