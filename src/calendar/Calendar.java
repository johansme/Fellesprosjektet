package calendar;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

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
	
	public int getCurrentWeekNumber()
	{	
		
		WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
		int weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear());
		return weekNumber;
		
	}
	
	
	public static void main(String args[]){
		
		Calendar c = new Calendar();
		System.out.println("Uke: " + c.getCurrentWeekNumber());
	}

}
