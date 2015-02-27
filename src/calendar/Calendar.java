package calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
	
	//Current here means the date etc. to be shown in the calendar.
	private LocalDate currentDate;
	private Month currentMonth;
	private List<Month> months;
	
	public Calendar() {
		currentDate = LocalDate.now();
		currentMonth = new Month(currentDate);
		months = new ArrayList<Month>();
		months.add(currentMonth);
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
		System.out.println(weekFields.weekBasedYear());
		System.out.println(weekFields.weekOfYear());
		return weekNumber;
		
	}

	
	public static void main(String args[]){
		
		Calendar c = new Calendar();
		System.out.println("Uke: " + c.getCurrentWeekNumber());
		
	}

	public List<Month> getMonths() {
		return months;
	}
	
	public Month getNextMonth() {
		int i = months.indexOf(currentMonth);
		if (i == months.size() - 1) {
			Month month = new Month(currentDate.plusMonths(1));
			months.add(month);
			currentDate = currentDate.plusMonths(1);
			currentDate.minusDays(currentDate.getDayOfMonth()-1);
			currentMonth = month;
			return month;
		} else {
			currentDate = currentDate.plusMonths(1);
			currentDate.minusDays(currentDate.getDayOfMonth()-1);
			currentMonth = months.get(i + 1);
			return months.get(i + 1);
		}
	}
	
	public Month getPreviousMonth() throws IllegalStateException {
		if (currentDate.isBefore(LocalDate.of(2000, 2, 1))) {
			throw new IllegalStateException("This calendar does not support dates prior to the year 2000");
		}
		int i = months.indexOf(currentMonth);
		if (i == 0) {
			Month month = new Month(currentDate.minusMonths(1));
			months.add(0, month);
			currentDate = currentDate.minusMonths(1);
			currentDate.plusDays(currentDate.lengthOfMonth()-currentDate.getDayOfMonth());
			currentMonth = month;
			return month;
		} else {
			currentDate = currentDate.minusMonths(1);
			currentDate.plusDays(currentDate.lengthOfMonth()-currentDate.getDayOfMonth());
			currentMonth = months.get(i - 1);
			return months.get(i - 1);
		}
	}

}
