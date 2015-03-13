package newAppointment;

import java.util.ArrayList;
import java.util.List;


public class ReceiveRoom {
	
	 
	private List<Tuple> roomList = new ArrayList<Tuple>();

	//return room values MeetingRoom(id, name, capacity)
	public void roomValues() {
		try{
//			while(true){
//				
//				String roomName = "";
//				int roomCapacity = 1;
//				if(roomName != "" && roomCapacity != 0 && roomName != null)
//				{
//					Tuple item = new Tuple(roomName,  roomCapacity );
//					roomList.add(item);
//				}
//			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("DB could not get room values");
		}
		

	}
	
	public void makeRoomQuery()
	{

	}
	
	public void setRoomList(List<Tuple> roomList)
	{
		this.roomList = roomList;
	}
	public List<Tuple> getRoomList(){
		
		return this.roomList; 
	}

}
