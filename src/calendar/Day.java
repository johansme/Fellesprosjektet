package calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {
	
	private LocalDate day;
	private List<Appointment> appointments;
	private int weekNumber;
	private List<DayChangeListener> dayChangeListeners;
	
	public Day(LocalDate date) {
		this.day = date;
		appointments = new ArrayList<Appointment>();
		dayChangeListeners = new ArrayList<DayChangeListener>();
	}

	public LocalDate getDate() {
		return day;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	public void removeAppointment(Appointment a) {
		if (appointments.contains(a)) {
			List<Appointment> oldAppointments = new ArrayList<Appointment>();
			oldAppointments.addAll(getAppointments());
			appointments.remove(a);
			fireDayChanged(oldAppointments);
		}
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointment != null && ! appointments.contains(appointment)) {
			List<Appointment> oldAppointments = new ArrayList<Appointment>();
			oldAppointments.addAll(getAppointments());
			appointments.add(appointment);
			fireDayChanged(oldAppointments);
		}
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}
	
	public void addChangeListener(DayChangeListener listener) {
		if (listener != null && ! dayChangeListeners.contains(listener)) {
			dayChangeListeners.add(listener);
		}
	}
	
	public void removeChangeListener(DayChangeListener listener) {
		if (dayChangeListeners.contains(listener)) {
			dayChangeListeners.remove(listener);
		}
	}
	
	private void fireDayChanged(List<Appointment> oldAppointments) {
		for (DayChangeListener listener : dayChangeListeners) {
			listener.dayChanged(this, oldAppointments, getAppointments());
		}
	}
	
	public boolean getDirty() {
		boolean dirty = false;
		for (Appointment appointment : appointments) {
			if (! appointment.getOpened()) {
				dirty = true;
			}
		}
		return dirty;
	}

}
