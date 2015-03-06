package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import newAppointment.ReceiveRoom;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

public class Appointment {

	private int id;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String location;
	private List<String> participants;
	private int overlap;
	private boolean opened=false;
	private boolean admin;
	private Day day;
	private Calendar calendar;
	private String attending;
	private List<String> roomList;
	private int roomCapacity;
	
	
	public Appointment() {
		participants = new ArrayList<String>();
		//hahahahhahahahah:D
		//TODO
		
		Random rand = new Random();

		id = rand.nextInt(57436) + 1;
		
	}

	public void addAppointmentToDay() {
		if (getDay() != null) {
			day.removeAppointment(this);
		}
		boolean added = false;
		if (calendar.getCurrentDate().getYear() == startDate.getYear() && calendar.getCurrentDate().getMonthValue() == startDate.getMonthValue()) {
			this.day = calendar.getCurrentMonth().getDay(startDate.getDayOfMonth());
			day.addAppointment(this);
			added = true;
		} else {
			for (Month month : calendar.getMonths()) {
				if (month.getYear() == startDate.getYear() && month.getMonth().equals(startDate.getMonth().toString())) {
					this.day = month.getDay(startDate.getDayOfMonth());
					day.addAppointment(this);
					added = true;
				}
			}
		}
		if (added == false) {
			if (startDate.isBefore(calendar.getMonths().get(0).getDay(1).getDate())) {
				int i = 0;
				if (startDate.getYear() == calendar.getMonths().get(0).getYear()) {
					i = calendar.getMonths().get(0).getDay(1).getDate().getMonthValue() - startDate.getMonthValue();
				} else {
					i = startDate.getMonthValue() + (calendar.getMonths().get(0).getYear() - startDate.getYear() - 1)*12 + (12 - calendar.getMonths().get(0).getDay(1).getDate().getMonthValue());
				}
				calendar.addPastMonths(i);
				this.day = calendar.getMonths().get(0).getDay(startDate.getDayOfMonth());
				day.addAppointment(this);
			} else {
				int i = 0;
				if (startDate.getYear() == calendar.getMonths().get(0).getYear()) {
					i = startDate.getMonthValue() - calendar.getMonths().get(0).getDay(1).getDate().getMonthValue();
				} else {
					i = calendar.getMonths().get(0).getDay(1).getDate().getMonthValue() + (startDate.getYear() - calendar.getMonths().get(0).getYear() - 1)*12 + (12 - startDate.getMonthValue());
				}
				calendar.addFutureMonths(i);
				this.day = calendar.getMonths().get(calendar.getMonths().size()-1).getDay(startDate.getDayOfMonth());
				day.addAppointment(this);
			}
		}
	}

	public void setData(Calendar calendar) {
		if (calendar != null) {
			this.calendar = calendar;
		}
	}

	public Calendar getCalendar() {
		return calendar;
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

	public void setParticipants(List<String> p) {
		if (p!=null) {
			for (String participant : p) {
				if (participantIsValid(participant)) {
					participants.add(participant);
				}
			}
		}
		else {
			participants=null;
		}
	}
	
	public void addParticipant(String user) {
		participants.add(user);
	}
	

	public List<String> getParticipants() {
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
		if (a=="Y" || a=="N" || a=="None" || a=="notAnswered") {
			attending=a;
		}
	}
	
	public String getAttending() {
		return attending;
	}
	
	public Day getDay() {
		return day;
	}
	
	public void setCapacity(int capacity) {
		
		this.roomCapacity = capacity;
	}
	public int getCapacity() {
		
		return roomCapacity;
		
	}

	
	public List<String> getRoomList(){
		return roomList;
	}

	
	
}
