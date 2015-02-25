package calendar;

import java.time.LocalDate;
import java.util.List;

public class Day {
	
	private LocalDate day;
	private List<Appointment> appointments;

	public LocalDate getDay() {
		return day;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

}
