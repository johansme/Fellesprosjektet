package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Appointment {
	
	private int id;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String location;
	private ArrayList<String> participants;
	private int overlap;
	private boolean opened=false;
	private boolean admin;
	private String attending;
	
	public Appointment() {
		
	}
	
	public void setDescription(String d) {
		if (descriptionIsValid(d)) {
			description = d;
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setStartDate(LocalDate d) {
		if (startDateIsValid(d)) {
			startDate = d;
		}
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setEndDate(LocalDate d) {
		if (endDateIsValid(d)) {
			endDate = d;
		}
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setStartTime(LocalTime t) {
		if (startTimeIsValid(t)) {
			startTime = t;
		}
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void setEndTime(LocalTime t) {
		if (endTimeIsValid(t)) {
			endTime = t;
		}
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setLocation(String l) {
		if (locationIsValid(l)) {
			location = l;
		}
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setParticipants(ArrayList<String> p) {
		for (String participant : p) {
			if (participantIsValid(participant)) {
				participants.add(participant);
			}
		}
	}
	
	public ArrayList<String> getParticipants() {
		return participants;
	}
	
	private boolean descriptionIsValid(String d) {
		return true;
	}
	
	private boolean startDateIsValid(LocalDate d) {
		if (LocalDate.now().isAfter(d)) {
			return false;
		}
		if (endDate!=null) {
			if (d.isAfter(endDate)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean endDateIsValid(LocalDate d) {
		if (LocalDate.now().isAfter(d)) {
			return false;
		}
		if (startDate!=null) {
			if (startDate.isAfter(d)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean startTimeIsValid(LocalTime t) {
		if (startDate==endDate) {
			if (endTime!=null) {
				if (t.isAfter(endTime)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean endTimeIsValid(LocalTime t) {
		if (startDate==endDate) {
			if (startTime!=null) {
				if (startTime.isAfter(t)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean locationIsValid(String l) {
		return true;
	}
	
	private boolean participantIsValid(String participant) {
		return true;
	}
	
	public int getOverlap() {
		return overlap;
	}
	
	public void setOverlap(int o) {
		overlap=o;
	}
	
	public void setID(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean getOpened() {
		return opened;
	}
	
	public void setOpened(boolean b) {
		opened = b;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean b) {
		admin = b;
	}
	
	public void setAttending(String a) {
		if (a=="Y" || a=="N" || a=="None") {
			attending=a;
		}
	}
	
	public String getAttending() {
		return attending;
	}
	

}
