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

	public LocalDate getDate() {
		return day;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	public void removeAppointment(Appointment a) {
		appointments.remove(a);
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointment != null && ! appointments.contains(appointment)) {
			appointments.add(appointment);
		}
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}

}
