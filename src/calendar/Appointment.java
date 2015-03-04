package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

	private Calendar calendar;

	public Appointment() {
		participants = new ArrayList<String>();
	}

	public void addAppointmentToDay() {
		boolean added = false;
		if (calendar.getCurrentDate().getYear() == startDate.getYear() && calendar.getCurrentDate().getMonthValue() == startDate.getMonthValue()) {
			Day day = calendar.getCurrentMonth().getDay(startDate.getDayOfMonth());
			day.addAppointment(this);
			added = true;
		} else {
			for (Month month : calendar.getMonths()) {
				if (month.getYear() == startDate.getYear() && month.getMonth().equals(startDate.getMonth().toString())) {
					month.getDay(startDate.getDayOfMonth()).addAppointment(this);
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
				calendar.getMonths().get(0).getDay(startDate.getDayOfMonth()).addAppointment(this);
			} else {
				int i = 0;
				if (startDate.getYear() == calendar.getMonths().get(0).getYear()) {
					i = startDate.getMonthValue() - calendar.getMonths().get(0).getDay(1).getDate().getMonthValue();
				} else {
					i = calendar.getMonths().get(0).getDay(1).getDate().getMonthValue() + (startDate.getYear() - calendar.getMonths().get(0).getYear() - 1)*12 + (12 - startDate.getMonthValue());
				}
				calendar.addFutureMonths(i);
				calendar.getMonths().get(calendar.getMonths().size()-1).getDay(startDate.getDayOfMonth()).addAppointment(this);
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
		for (String participant : p) {
			if (participantIsValid(participant)) {
				participants.add(participant);
			}
		}
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


}
