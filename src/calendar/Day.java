package calendar;

import java.time.LocalDate;
import java.util.List;

public class Day {
	
	private LocalDate day;
	private List<Appointment> appointments;
	private int weekNumber;

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
