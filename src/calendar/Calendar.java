package calendar;

import java.time.LocalDate;

public class Calendar {
	
	//Current here means the date etc. to be shown in the calendar.
	private LocalDate currentDate;
	private Month currentMonth;
	
	public Calendar() {
		currentDate = LocalDate.now();
	}
	
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	
	public Month getCurrentMonth() {
		return currentMonth;
	}

}
