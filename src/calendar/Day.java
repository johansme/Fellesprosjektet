package calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {
	
	private LocalDate day;
	private List<Appointment> appointments;
	private int weekNumber;
	
	public Day(LocalDate date) {
		this.day = date;
		appointments = new ArrayList<Appointment>();
	}

	public LocalDate getDay() {
		return day;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}

}
