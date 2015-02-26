package calendar;

import java.util.List;

public interface DayChangeListener {
	
	public void dayChanged(Day day, List<Appointment> oldAppointments, List<Appointment> newAppointment);

}
