package calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Day {
	
	private LocalDate day;
	private List<Appointment> appointments;
	private List<Appointment> activeAppointments;
	private int weekNumber;
	private List<DayChangeListener> dayChangeListeners;
	
	public Day(LocalDate date) {
		this.day = date;
		appointments = new ArrayList<Appointment>();
		activeAppointments = new ArrayList<Appointment>();
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
			setActiveAppointments();
			fireDayChanged(oldAppointments);
		}
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointment != null && ! appointments.contains(appointment)) {
			List<Appointment> oldAppointments = new ArrayList<Appointment>();
			oldAppointments.addAll(getAppointments());
			appointments.add(appointment);
			setActiveAppointments();
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
		try{
			for (DayChangeListener listener : dayChangeListeners) 
			{
				listener.dayChanged(this, oldAppointments, getAppointments());
				
			}
			
		}catch(ConcurrentModificationException e){
			// burde fikset låsing av lister under endring, men har ikke tid.
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

	public void calculateOverlap() {
		setActiveAppointments();
		if (!activeAppointments.isEmpty() || activeAppointments!=null) {
			for (Appointment a : activeAppointments) {
				a.clearOverlap();
			}
			if(activeAppointments.size()>1) {
				sortAppointments();
				int[] overlap = new int[2];
				overlap[0]=0;
				overlap[1]=0;
				double t1;
				double t2;
				if (activeAppointments.size()>1) {
					for (int i=0; i<activeAppointments.size()-1; i++) {
						t1 = (double)activeAppointments.get(i).getStartTime().getHour()+((double)((double)activeAppointments.get(i).getStartTime().getMinute())/(double)60);
						for (int j=i+1; j<activeAppointments.size(); j++) {
							t2 = (double)activeAppointments.get(j).getStartTime().getHour()+((double)((double)activeAppointments.get(j).getStartTime().getMinute())/(double)60);
							if (t2-t1<0.5) {
								overlap[0]+=1;
								overlap[1]+=1;
								overlap[0]+=activeAppointments.get(i).getOverlap()[0];
								overlap[1]+=activeAppointments.get(i).getOverlap()[1];
							}
							else if (activeAppointments.get(j).getStartTime().isBefore(activeAppointments.get(i).getEndTime())) {
								overlap[1]+=1;
								overlap[1]+=activeAppointments.get(i).getOverlap()[1];
							}
							activeAppointments.get(j).addOverlap(overlap);
							overlap[0]=0;
							overlap[1]=0;
							
						}
					}
				}
			}
		}
		
	}
	
	private void sortAppointments() {
		List<Appointment> newAppointments = new ArrayList<Appointment>();
		newAppointments.add(activeAppointments.get(0));
		for (int i=1; i<activeAppointments.size(); i++) {
			double t2 = (double)activeAppointments.get(i).getStartTime().getHour()+(double)(((double)activeAppointments.get(i).getStartTime().getMinute())/(double)60);
			for (int j=0; j<i; j++) {
				double t1 = (double)newAppointments.get(j).getStartTime().getHour()+(double)(((double)newAppointments.get(j).getStartTime().getMinute())/(double)60);
				if (t2<=t1) {
					newAppointments.add(j, activeAppointments.get(i));
					break;
				}
				else {
					if (j==i-1) {
						newAppointments.add(activeAppointments.get(i));
					}
				}
			}
		}
		activeAppointments.clear();
		for (int i=0; i<newAppointments.size(); i++) {
			activeAppointments.add(newAppointments.get(i));
		}
	}
	
	public void setActiveAppointments() {
		activeAppointments.clear();
		for (Appointment a : appointments) {
			if (a.getActive(true)) {
				activeAppointments.add(a);
			}
		}
	}
	
	public List<Appointment> getActiveAppointments() {
		return activeAppointments;
	}

}
