package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Appointment {
	
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String location;
	private ArrayList<String> participants;
	
	public String getDescription() {
		return description;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public String getLocation() {
		return location;
	}
	
	public ArrayList<String> getParticipants() {
		return participants;
	}
	

}
