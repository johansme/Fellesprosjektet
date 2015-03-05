package newAppointment;

import java.time.LocalDate;
import java.util.List;

public class NewAppointmentManager {

// rigging up variables;
private String description;
private LocalDate startDate;
private LocalDate endDate;
private String startTime;
private String endTime;
private int roomCapasity;
private List<String> participants;

// setters for private variables:

public void setDescription(String desc){
	description = desc;
}
public void setStartDate(LocalDate startDate){
	this.startDate = startDate;
}
public void setEndDate(LocalDate endDate){
	this.endDate = endDate;
}
public void setStartTime(String startTime){
	this.startTime = startTime;
}
public void setEndTime(String endTime){
	this.endTime = endTime;
}
public void setRoomCapasity(int cap){
	roomCapasity = cap;
}

public void setParticipants(List<String> partc){
	participants = partc;
}

// getters for all the variables;

public String getDescription(){
	return description;
}
public LocalDate getStartDate(){
	return startDate;
}
public LocalDate getEndDate(){
	return endDate;
}
public String getStartTime(){
	return startTime;
}
public String getEndTime(){
	return endTime;
}

//roomCapacity from user input
public int getRoomCapacity(){
	return roomCapasity;
}
public List<String> getParticipants(){
	return participants;
}

public void printData(){
	System.out.println("description: " + description);
	System.out.println("startdate : " + startDate.toString());
	System.out.println("startTime : " + startTime);
	System.out.println("end date : "+ endDate.toString());
	System.out.println("end Time : " + endTime);
	System.out.println("room: " );
	System.out.println("capasity: " + roomCapasity);
	System.out.println("participants: " + participants);
}


	
	
}
